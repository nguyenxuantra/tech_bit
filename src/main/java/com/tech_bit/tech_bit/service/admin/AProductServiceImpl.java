package com.tech_bit.tech_bit.service.admin;

import com.tech_bit.tech_bit.common.pageResponse.PageResponse;
import com.tech_bit.tech_bit.dto.request.ProductRequest;
import com.tech_bit.tech_bit.dto.response.ProductResponse;


public interface AProductServiceImpl {
    void createProduct(ProductRequest request);
    PageResponse<ProductResponse> getAllProducts(String search, Long fromDate, Long toDate, Integer categoryId, String sortBy, String sortDir, int page, int size);
    void deleteProduct(Integer productId);
    void updateProduct(Integer productId, ProductRequest request);
    ProductResponse getProductDetail(Integer productId);
}
