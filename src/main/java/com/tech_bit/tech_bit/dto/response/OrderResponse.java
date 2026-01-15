package com.tech_bit.tech_bit.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
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
