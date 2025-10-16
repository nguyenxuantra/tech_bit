package com.tech_bit.tech_bit.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductResponse {
    Integer productId;
    String name;
    String description;
    double price;
    double discount;
    Integer stock;
    String imageUrl;
    double rating;
    String brand;
    String categoryName;
    Long createdAt;
}
