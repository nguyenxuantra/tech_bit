package com.tech_bit.tech_bit.service.admin;

import com.tech_bit.tech_bit.dto.response.*;
import com.tech_bit.tech_bit.entity.Order;
import com.tech_bit.tech_bit.entity.OrderItem;
import com.tech_bit.tech_bit.entity.Product;
import com.tech_bit.tech_bit.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AStatisticsServiceImpl implements AStatisticsService {

    UserRepository userRepository;
    OrderRepository orderRepository;
    ProductRepository productRepository;
    OrderItemRepository orderItemRepository;

    @Override
    public OverviewKPIResponse getOverviewKPI() {
        // Total counts
        long totalUsers = userRepository.count();
        long totalOrders = orderRepository.count();
        long totalProducts = productRepository.count();

        // Total revenue - sum of all order amounts
        List<Order> allOrders = orderRepository.findAll();
        long totalRevenue = allOrders.stream()
                .mapToLong(order -> order.getTotalAmount() != null ? order.getTotalAmount().longValue() : 0L)
                .sum();

        // Today's statistics
        long todayStart = getTodayStartTimestamp();
        long todayEnd = todayStart + (24 * 60 * 60 * 1000) - 1; // End of today

        long newUsersToday = userRepository.findAll().stream()
                .filter(user -> user.getCreatedAt() != null && 
                        user.getCreatedAt() >= todayStart && user.getCreatedAt() <= todayEnd)
                .count();

        long ordersToday = allOrders.stream()
                .filter(order -> order.getCreatedAt() != null &&
                        order.getCreatedAt() >= todayStart && order.getCreatedAt() <= todayEnd)
                .count();

        long revenueToday = allOrders.stream()
                .filter(order -> order.getCreatedAt() != null &&
                        order.getCreatedAt() >= todayStart && order.getCreatedAt() <= todayEnd)
                .mapToLong(order -> order.getTotalAmount() != null ? order.getTotalAmount().longValue() : 0L)
                .sum();

        return OverviewKPIResponse.builder()
                .totalUsers(totalUsers)
                .totalOrders(totalOrders)
                .totalRevenue(totalRevenue)
                .totalProducts(totalProducts)
                .newUsersToday(newUsersToday)
                .ordersToday(ordersToday)
                .revenueToday(revenueToday)
                .build();
    }

    @Override
    public List<TimeStatisticsResponse> getTimeStatistics(String fromDate, String toDate, String groupBy) {
        // Nếu không truyền date, mặc định lấy tháng hiện tại
        if (fromDate == null || fromDate.isEmpty()) {
            fromDate = getCurrentMonthStartDate();
        }
        if (toDate == null || toDate.isEmpty()) {
            toDate = getCurrentMonthEndDate();
        }

        // Parse date strings (format: yyyy-MM-dd)
        long fromTimestamp = parseDateToTimestamp(fromDate);
        long toTimestamp = parseDateToTimestamp(toDate) + (24 * 60 * 60 * 1000) - 1; // End of day

        // Get orders in date range
        List<Order> ordersInRange = orderRepository.findAll().stream()
                .filter(order -> order.getCreatedAt() != null &&
                        order.getCreatedAt() >= fromTimestamp && order.getCreatedAt() <= toTimestamp)
                .collect(Collectors.toList());

        // Group by date based on groupBy parameter
        Map<String, Long> revenueByDate = new TreeMap<>();

        for (Order order : ordersInRange) {
            String dateKey = formatTimestampByGroup(order.getCreatedAt(), groupBy);
            long revenue = order.getTotalAmount() != null ? order.getTotalAmount().longValue() : 0L;
            revenueByDate.merge(dateKey, revenue, Long::sum);
        }

        // Convert to response list
        return revenueByDate.entrySet().stream()
                .map(entry -> TimeStatisticsResponse.builder()
                        .date(entry.getKey())
                        .revenue(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public OrderStatisticsResponse getOrderStatistics() {
        List<Order> allOrders = orderRepository.findAll();
        
        long total = allOrders.size();
        long success = allOrders.stream()
                .filter(order -> "SUCCESS".equalsIgnoreCase(order.getStatus()))
                .count();
        long approved = allOrders.stream()
                .filter(order -> "APPROVED".equalsIgnoreCase(order.getStatus()))
                .count();
        long pending = allOrders.stream()
                .filter(order -> "PENDING".equalsIgnoreCase(order.getStatus()))
                .count();

        return OrderStatisticsResponse.builder()
                .total(total)
                .success(success)
                .approved(approved)
                .pending(pending)
                .build();
    }

    @Override
    public List<TopProductResponse> getTopProducts() {
        // Get all order items
        List<OrderItem> allOrderItems = orderItemRepository.findAll();
        
        // Get all orders to filter by SUCCESS or APPROVED status
        Map<Long, Order> ordersMap = orderRepository.findAll().stream()
                .collect(Collectors.toMap(Order::getOrderId, order -> order));

        // Filter order items by order status (only SUCCESS or APPROVED)
        List<OrderItem> validOrderItems = allOrderItems.stream()
                .filter(item -> {
                    Order order = ordersMap.get(item.getOrder().getOrderId());
                    return order != null && 
                           ("SUCCESS".equalsIgnoreCase(order.getStatus()) || 
                            "APPROVED".equalsIgnoreCase(order.getStatus()));
                })
                .collect(Collectors.toList());

        // Group by productId and calculate quantity and revenue
        Map<Integer, ProductStats> productStatsMap = new HashMap<>();
        
        for (OrderItem item : validOrderItems) {
            Integer productId = item.getProductId();
            int quantity = item.getQuantity() != null ? item.getQuantity() : 0;
            double price = item.getPrice() != null ? item.getPrice() : 0.0;
            long revenue = (long) (price * quantity);

            productStatsMap.computeIfAbsent(productId, k -> new ProductStats(productId, 0, 0L))
                    .addQuantity(quantity)
                    .addRevenue(revenue);
        }

        // Get product names
        Map<Integer, String> productNamesMap = productRepository.findAll().stream()
                .collect(Collectors.toMap(Product::getProductId, Product::getName));

        // Convert to response and sort by revenue descending, limit to top products
        return productStatsMap.entrySet().stream()
                .map(entry -> {
                    Integer productId = entry.getKey();
                    ProductStats stats = entry.getValue();
                    String productName = productNamesMap.getOrDefault(productId, "Unknown Product");
                    
                    return TopProductResponse.builder()
                            .productId(productId)
                            .name(productName)
                            .quantity(stats.quantity)
                            .revenue(stats.revenue)
                            .build();
                })
                .sorted((a, b) -> Long.compare(b.getRevenue(), a.getRevenue()))
                .limit(10) // Top 10 products
                .collect(Collectors.toList());
    }

    // Helper methods
    private long getTodayStartTimestamp() {
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        return startOfDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    private long parseDateToTimestamp(String dateStr) {
        LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
        LocalDateTime startOfDay = date.atStartOfDay();
        return startOfDay.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    private String getCurrentMonthStartDate() {
        LocalDate today = LocalDate.now();
        LocalDate firstDayOfMonth = today.withDayOfMonth(1);
        return firstDayOfMonth.format(DateTimeFormatter.ISO_DATE);
    }

    private String getCurrentMonthEndDate() {
        LocalDate today = LocalDate.now();
        LocalDate lastDayOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        return lastDayOfMonth.format(DateTimeFormatter.ISO_DATE);
    }

    private String formatTimestampByGroup(long timestamp, String groupBy) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timestamp), 
                ZoneId.systemDefault()
        );

        DateTimeFormatter formatter;
        switch (groupBy.toUpperCase()) {
            case "MONTH":
                formatter = DateTimeFormatter.ofPattern("yyyy-MM");
                return dateTime.format(formatter);
            case "YEAR":
                formatter = DateTimeFormatter.ofPattern("yyyy");
                return dateTime.format(formatter);
            case "DAY":
            default:
                formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                return dateTime.format(formatter);
        }
    }

    // Inner class for product statistics
    private static class ProductStats {
        Integer productId;
        Integer quantity;
        Long revenue;

        ProductStats(Integer productId, Integer quantity, Long revenue) {
            this.productId = productId;
            this.quantity = quantity;
            this.revenue = revenue;
        }

        ProductStats addQuantity(int qty) {
            this.quantity += qty;
            return this;
        }

        ProductStats addRevenue(long rev) {
            this.revenue += rev;
            return this;
        }
    }
}
