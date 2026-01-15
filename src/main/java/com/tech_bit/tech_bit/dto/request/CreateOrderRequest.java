package com.tech_bit.tech_bit.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateOrderRequest {
    Integer addressId;
    Integer couponId; // Optional
}
