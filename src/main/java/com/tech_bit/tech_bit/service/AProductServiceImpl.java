package com.tech_bit.tech_bit.service;

import com.tech_bit.tech_bit.common.pageResponse.PageResponse;
import com.tech_bit.tech_bit.dto.request.ProductRequest;
import com.tech_bit.tech_bit.dto.response.ProductResponse;


public interface AProductServiceImpl {
    void createProduct(ProductRequest request);
    PageResponse<ProductResponse> getAllProducts(String search, Long fromDate, Long toDate, String sortBy, String sortDir, int page, int size);
    void deleteProduct(Integer productId);
    void updateProduct(Integer productId, ProductRequest request);
}
