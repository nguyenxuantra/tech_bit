package com.tech_bit.tech_bit.controller;

import com.tech_bit.tech_bit.common.apiResponse.ApiResponse;
import com.tech_bit.tech_bit.dto.request.ACategoryRequest;
import com.tech_bit.tech_bit.dto.response.ACategoryResponse;
import com.tech_bit.tech_bit.service.ACategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class ACategoryController {

    @Autowired
    private ACategoryService categoryService;


    @PostMapping
    public ApiResponse<Void> createCategory(@RequestBody ACategoryRequest categoryRequest) {
        categoryService.createCategory(categoryRequest);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Tạo danh mục thành công")
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<ACategoryResponse> updateCategory(
            @PathVariable("id") Integer categoryId,
            @RequestBody ACategoryRequest categoryRequest) {
        categoryService.updateCategory(categoryId, categoryRequest);
        return ApiResponse.<ACategoryResponse>builder()
                .code(200)
                .message("Cập nhật danh mục thành công")
                .build();
    }


    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteCategory(@PathVariable("id") Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Xóa danh mục thành công")
                .build();
    }
}
