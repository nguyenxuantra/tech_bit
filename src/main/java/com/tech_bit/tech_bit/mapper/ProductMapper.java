package com.tech_bit.tech_bit.mapper;

import com.tech_bit.tech_bit.dto.request.ProductRequest;
import com.tech_bit.tech_bit.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    Product toProduct(ProductRequest request);

    void updateProduct(@MappingTarget Product product, ProductRequest request);
}
