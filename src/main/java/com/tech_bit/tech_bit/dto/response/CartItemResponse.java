package com.tech_bit.tech_bit.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartItemResponse {
    Integer cartItemId;
    Integer productId;
    String productName;
    Double productPrice;
    String productImageUrl;
    Integer quantity;
    Long createdAt;
}
