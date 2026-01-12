package com.tech_bit.tech_bit.controller;

import com.tech_bit.tech_bit.common.apiResponse.ApiResponse;
import com.tech_bit.tech_bit.common.pageResponse.PageResponse;
import com.tech_bit.tech_bit.dto.request.CreateOrderRequest;
import com.tech_bit.tech_bit.dto.response.OrderResponse;
import com.tech_bit.tech_bit.service.OrderService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OrderController {

    OrderService orderService;

    // Tạo đơn hàng
    @PostMapping
    public ApiResponse<OrderResponse> createOrder(@RequestBody CreateOrderRequest request) {
        OrderResponse orderResponse = orderService.createOrder(request);
        return ApiResponse.<OrderResponse>builder()
                .code(200)
                .message("Tạo đơn hàng thành công")
                .result(orderResponse)
                .build();
    }

    // Duyệt đơn hàng (chỉ admin)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{orderId}/approve")
    public ApiResponse<OrderResponse> approveOrder(@PathVariable Long orderId) {
        OrderResponse orderResponse = orderService.approveOrder(orderId);
        return ApiResponse.<OrderResponse>builder()
                .code(200)
                .message("Duyệt đơn hàng thành công")
                .result(orderResponse)
                .build();
    }

    // Lấy danh sách đơn hàng
    @GetMapping
    public ApiResponse<PageResponse<OrderResponse>> getOrders(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String status) {
        PageResponse<OrderResponse> orders = orderService.getOrders(pageNo, pageSize, status);
        return ApiResponse.<PageResponse<OrderResponse>>builder()
                .code(200)
                .message("Success")
                .result(orders)
                .build();
    }

    // Lấy chi tiết đơn hàng
    @GetMapping("/{orderId}")
    public ApiResponse<OrderResponse> getOrderById(@PathVariable Long orderId) {
        OrderResponse orderResponse = orderService.getOrderById(orderId);
        return ApiResponse.<OrderResponse>builder()
                .code(200)
                .message("Success")
                .result(orderResponse)
                .build();
    }
}
