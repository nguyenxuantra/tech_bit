package com.tech_bit.tech_bit.service;

import com.tech_bit.tech_bit.common.pageResponse.PageResponse;
import com.tech_bit.tech_bit.dto.request.ACategoryRequest;
import com.tech_bit.tech_bit.dto.response.ACategoryResponse;
import com.tech_bit.tech_bit.entity.Categories;
import com.tech_bit.tech_bit.exception.AppException;
import com.tech_bit.tech_bit.exception.ErrorCode;
import com.tech_bit.tech_bit.repository.CategoriesRepository;
import com.tech_bit.tech_bit.specification.CategorySpecification;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    @Override
    public PageResponse<ACategoryResponse> getListCategories (String search, Long fromDate, Long toDate, String sortBy, String sortDir, int page, int size){
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page-1, size, sort);
        Page<Categories> categories = categoriesRepository.findAll(CategorySpecification.searchAllFields(search, fromDate, toDate),pageable );
        List<ACategoryResponse> categorysResult = categories.stream().map(category->
            ACategoryResponse.builder()
            .categoryId(category.getCategoryId())
            .name(category.getName())
            .imageUrl(category.getImageUrl())
            .createdAt(category.getCreatedAt())
            .build()
        ).toList();
        return  PageResponse.<ACategoryResponse>builder()
        .content(categorysResult)
        .pageNo(categories.getNumber())
        .pageSize(categories.getSize())
        .totalPages(categories.getTotalPages())
        .totalElement(categories.getTotalElements())
        .last(categories.isLast())
        .build();
    }
}
