package com.tech_bit.tech_bit.service;

import com.tech_bit.tech_bit.dto.request.ACategoryRequest;
import com.tech_bit.tech_bit.entity.Categories;
import com.tech_bit.tech_bit.exception.AppException;
import com.tech_bit.tech_bit.exception.ErrorCode;
import com.tech_bit.tech_bit.repository.CategoriesRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ACategoryServiceImpl implements ACategoryService {

    CategoriesRepository categoriesRepository;

    @Override
    public void createCategory(ACategoryRequest categoryRequest) {
        Categories category = Categories.builder()
                .name(categoryRequest.getName())
                .imageUrl(categoryRequest.getImageUrl())
                .build();
        categoriesRepository.save(category);
    }


    @Override
    public void updateCategory(Integer categoryId, ACategoryRequest categoryRequest) {
        Categories category = categoriesRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));

        category.setName(categoryRequest.getName());
        category.setImageUrl(categoryRequest.getImageUrl());

        categoriesRepository.save(category);


    }

    @Override
    public void deleteCategory(Integer categoryId) {
        Categories category = categoriesRepository.findById(categoryId)
                .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        categoriesRepository.delete(category);
    }
}
