package com.tech_bit.tech_bit.controller;

import com.tech_bit.tech_bit.common.apiResponse.ApiResponse;
import com.tech_bit.tech_bit.common.pageResponse.PageResponse;
import com.tech_bit.tech_bit.dto.request.ACategoryRequest;
import com.tech_bit.tech_bit.dto.response.ACategoryResponse;
import com.tech_bit.tech_bit.service.admin.ACategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/categories")
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
    @GetMapping()
    ApiResponse<PageResponse<ACategoryResponse>> getListProduct(
        @RequestParam(value="search", required =false) String search,
        @RequestParam(value="from_date", required = false) Long fromDate,
        @RequestParam(value="to_date", required = false) Long toDate, 
        @RequestParam(value="sort_by", required = false, defaultValue ="categoryId") String sortBy,
        @RequestParam(value="sort_dir", required = false, defaultValue = "desc") String sortDir,
        @RequestParam(value="page_no", required = false, defaultValue = "1") int pageNo,
        @RequestParam(value="page_size", required = false, defaultValue = "5" ) int pageSize
    ){
        return ApiResponse.<PageResponse<ACategoryResponse>>builder()
        .code(200)
        .message("get success")
        .result(categoryService.getListCategories(search, fromDate, toDate, sortBy, sortDir, pageNo, pageSize))
        .build();
    }
}
