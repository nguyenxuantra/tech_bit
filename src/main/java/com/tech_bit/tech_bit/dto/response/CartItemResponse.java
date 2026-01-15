package com.tech_bit.tech_bit.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
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
