package com.tech_bit.tech_bit.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItemResponse {
    Long orderItemId;
    Integer productId;
    String productName;
    String productImageUrl;
    Integer quantity;
    Double price;
}
