package com.tech_bit.tech_bit.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductRequest {
    String name;
    String description;
    Double price;
    Double discount;
    Integer stock;
    String brand;
    Integer categoryId;
    String imageUrl;
    String publicId;
}
