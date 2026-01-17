package com.tech_bit.tech_bit.service;

import com.tech_bit.tech_bit.common.pageResponse.PageResponse;
import com.tech_bit.tech_bit.dto.response.ProductResponse;

public interface ProductService {
    PageResponse<ProductResponse> getProducts(String search, Integer categoryId, Double minPrice, Double maxPrice, Boolean flashSale, String sortBy, String sortDir, int page, int size);
}
