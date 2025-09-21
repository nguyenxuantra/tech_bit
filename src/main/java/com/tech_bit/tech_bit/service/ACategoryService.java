package com.tech_bit.tech_bit.service;

import com.tech_bit.tech_bit.common.pageResponse.PageResponse;
import com.tech_bit.tech_bit.dto.request.ACategoryRequest;
import com.tech_bit.tech_bit.dto.response.ACategoryResponse;

public interface ACategoryService {
    
    void createCategory(ACategoryRequest categoryRequest);

    void updateCategory(Integer categoryId, ACategoryRequest categoryRequest);

    void deleteCategory(Integer categoryId);

    PageResponse<ACategoryResponse> getListCategories (String search, Long fromDate, Long toDate, String sortBy, String sortDir, int page, int size);
}
