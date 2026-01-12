package com.tech_bit.tech_bit.service;

import com.tech_bit.tech_bit.dto.request.CreateOrderRequest;
import com.tech_bit.tech_bit.dto.response.OrderResponse;
import com.tech_bit.tech_bit.common.pageResponse.PageResponse;

public interface OrderService {
    OrderResponse createOrder(CreateOrderRequest request);
    OrderResponse approveOrder(Long orderId);
    PageResponse<OrderResponse> getOrders(Integer pageNo, Integer pageSize, String status);
    OrderResponse getOrderById(Long orderId);
}
