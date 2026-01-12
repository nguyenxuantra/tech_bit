package com.tech_bit.tech_bit.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderResponse {
    Long orderId;
    Integer userId;
    Double totalAmount;
    String status;
    Long createdAt;
    Integer addressId;
    Integer couponId;
    List<OrderItemResponse> items;
}
