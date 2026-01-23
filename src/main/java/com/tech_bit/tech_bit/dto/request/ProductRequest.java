package com.tech_bit.tech_bit.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String name;
    private String description;
    private Double price;
    private Double discount;
    private Integer stock;
    private String brand;
    private Integer categoryId;
    private String imageUrl;
}
