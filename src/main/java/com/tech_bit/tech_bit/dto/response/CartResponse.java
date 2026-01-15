package com.tech_bit.tech_bit.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CartResponse {
    Integer cartId;
    Integer userId;
    List<CartItemResponse> items;
    Long updatedAt;
}
