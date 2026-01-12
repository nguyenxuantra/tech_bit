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
public class CartResponse {
    Integer cartId;
    Integer userId;
    List<CartItemResponse> items;
    Long updatedAt;
}
