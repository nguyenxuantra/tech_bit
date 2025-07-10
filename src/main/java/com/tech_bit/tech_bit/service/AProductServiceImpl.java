package com.tech_bit.tech_bit.service;

import com.tech_bit.tech_bit.dto.request.ProductRequest;
import com.tech_bit.tech_bit.entity.Product;

import java.util.List;

public interface AProductServiceImpl {
    void createProduct(ProductRequest request);
    List<Product> getAllProducts();
    void deleteProduct(Integer productId);
    void updateProduct(Integer productId, ProductRequest request);
}
