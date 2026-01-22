package com.tech_bit.tech_bit.service;

import com.tech_bit.tech_bit.common.pageResponse.PageResponse;
import com.tech_bit.tech_bit.dto.request.CreateOrderRequest;
import com.tech_bit.tech_bit.dto.response.OrderItemResponse;
import com.tech_bit.tech_bit.dto.response.OrderResponse;
import com.tech_bit.tech_bit.entity.*;
import com.tech_bit.tech_bit.exception.AppException;
import com.tech_bit.tech_bit.exception.ErrorCode;
import com.tech_bit.tech_bit.repository.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;
    OrderItemRepository orderItemRepository;
    CartRepository cartRepository;
    CartItemRepository cartItemRepository;
    ProductRepository productRepository;
    UserRepository userRepository;
    AddressesRepository addressesRepository;
    CouponsRepository couponsRepository;

    private Integer getCurrentUserId() {
        var context = SecurityContextHolder.getContext();
        String username = context.getAuthentication().getName();
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return user.getUserId();
    }

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {
        Integer userId = getCurrentUserId();

        // Validate address
//        if (request.getAddressId() != null) {
//            Addresses address = addressesRepository.findById(request.getAddressId())
//                    .orElseThrow(() -> new AppException(ErrorCode.ADDRESS_NOT_FOUND));
//            if (!address.getUserId().equals(userId)) {
//                throw new AppException(ErrorCode.ADDRESS_NOT_FOUND);
//            }
//        }

        // Get user's cart
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new AppException(ErrorCode.CART_NOT_FOUND));

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getCartId());
        if (cartItems.isEmpty()) {
            throw new AppException(ErrorCode.CART_IS_EMPTY);
        }

        // Calculate total amount
        double totalAmount = 50000;
        for (CartItem cartItem : cartItems) {
            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

            // Check stock
//            if (product.getStock() < cartItem.getQuantity()) {
//                throw new AppException(ErrorCode.INSUFFICIENT_STOCK);
//            }


            totalAmount +=  product.getDiscount();
        }

        // Apply coupon if provided
//        Double discountAmount = 0.0;
//        if (request.getCouponId() != null) {
//            Coupons coupon = couponsRepository.findById(request.getCouponId())
//                    .orElseThrow(() -> new AppException(ErrorCode.COUPON_NOT_FOUND));
//
//            // Validate coupon
//            if (!coupon.getIsActive()) {
//                throw new AppException(ErrorCode.COUPON_INACTIVE);
//            }
//            if (coupon.getExpiryDate() < System.currentTimeMillis()) {
//                throw new AppException(ErrorCode.COUPON_EXPIRED);
//            }
//
//            // Apply discount
//            if ("PERCENTAGE".equalsIgnoreCase(coupon.getDiscountType())) {
//                discountAmount = totalAmount * (coupon.getDiscountValue() / 100);
//            } else {
//                discountAmount = coupon.getDiscountValue();
//            }
//            totalAmount -= discountAmount;
//        }

        // Create order
        Order order = Order.builder()
                .userId(userId)
                .totalAmount(Math.max(0, totalAmount))
                .status("PENDING")
                .addressId(request.getAddressId())
                .couponId(request.getCouponId())
                .build();
        order = orderRepository.save(order);

        // Create order items and update product stock
        for (CartItem cartItem : cartItems) {
            Product product = productRepository.findById(cartItem.getProductId())
                    .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));

            double itemPrice = product.getDiscount();

            OrderItem orderItem = OrderItem.builder()
                    .order(order)
                    .productId(cartItem.getProductId())
                    .quantity(cartItem.getQuantity())
                    .price(itemPrice)
                    .build();
            orderItemRepository.save(orderItem);

            // Update product stock
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);
        }

        // Clear cart
        cartItemRepository.deleteByCartId(cart.getCartId());
        cartRepository.delete(cart);

        return getOrderById(order.getOrderId());
    }

    @Override
    @Transactional
    public OrderResponse approveOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        if (!"PENDING".equals(order.getStatus())) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        }

        order.setStatus("APPROVED");
        order = orderRepository.save(order);

        return getOrderById(order.getOrderId());
    }

    @Override
    public PageResponse<OrderResponse> getOrders(Integer pageNo, Integer pageSize, String status, String sortBy, String sortDir) {

        
        // Default sort by orderId descending if not provided
        String sortByField = (sortBy != null && !sortBy.isEmpty()) ? sortBy : "orderId";
        Sort.Direction direction = (sortDir != null && sortDir.equalsIgnoreCase("asc")) ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortByField);
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Order> orderPage;
        if (status != null && !status.isEmpty()) {
            orderPage = orderRepository.findByStatus( status, pageable);
        } else {
            orderPage = orderRepository.findAll(pageable);
        }

        List<OrderResponse> orderResponses = orderPage.getContent().stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());

        return PageResponse.<OrderResponse>builder()
                .content(orderResponses)
                .pageNo(orderPage.getNumber())
                .pageSize(orderPage.getSize())
                .totalElement(orderPage.getTotalElements())
                .totalPages(orderPage.getTotalPages())
                .last(orderPage.isLast())
                .build();
    }

    @Override
    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new AppException(ErrorCode.ORDER_NOT_FOUND));

        Integer userId = getCurrentUserId();
        if (!order.getUserId().equals(userId)) {
            throw new AppException(ErrorCode.ORDER_NOT_FOUND);
        }

        return mapToOrderResponse(order);
    }

    private OrderResponse mapToOrderResponse(Order order) {
        List<OrderItem> orderItems = orderItemRepository.findByOrder_OrderId(order.getOrderId());
        List<OrderItemResponse> itemResponses = orderItems.stream()
                .map(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new AppException(ErrorCode.PRODUCT_NOT_FOUND));
                    return OrderItemResponse.builder()
                            .orderItemId(item.getOrderItemId())
                            .productId(item.getProductId())
                            .productName(product.getName())
                            .productImageUrl(product.getImageUrl())
                            .quantity(item.getQuantity())
                            .price(item.getPrice())
                            .build();
                })
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .orderId(order.getOrderId())
                .userId(order.getUserId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus())
                .createdAt(order.getCreatedAt())
                .addressId(order.getAddressId())
                .couponId(order.getCouponId())
                .items(itemResponses)
                .build();
    }
}
