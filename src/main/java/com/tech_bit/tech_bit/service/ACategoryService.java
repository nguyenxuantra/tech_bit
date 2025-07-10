package com.tech_bit.tech_bit.service;

import com.tech_bit.tech_bit.dto.request.ACategoryRequest;
import com.tech_bit.tech_bit.dto.response.ACategoryResponse;
import com.tech_bit.tech_bit.entity.Categories;

import java.util.List;

public interface ACategoryService {


    void createCategory(ACategoryRequest categoryRequest);

    void updateCategory(Integer categoryId, ACategoryRequest categoryRequest);

    void deleteCategory(Integer categoryId);
}
