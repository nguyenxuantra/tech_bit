package com.tech_bit.tech_bit.dto.request;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductRequest {
    String name;
    String description;
    Double price;
    Integer quantity;
    String image;
    Integer categoryId;
}
