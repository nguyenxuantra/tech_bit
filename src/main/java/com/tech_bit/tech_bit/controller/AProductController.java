package com.tech_bit.tech_bit.controller;


import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tech_bit.tech_bit.common.apiResponse.ApiResponse;
import com.tech_bit.tech_bit.common.pageResponse.PageResponse;
import com.tech_bit.tech_bit.dto.request.ProductRequest;
import com.tech_bit.tech_bit.dto.response.ProductResponse;
import com.tech_bit.tech_bit.service.AProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/admin/product")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AProductController {
    AProductService productService;

    @PostMapping()
    ApiResponse<Void> createProduct(
        @RequestBody ProductRequest productRequest
    ){
        productService.createProduct(productRequest);
        return ApiResponse.<Void>builder()
        .code(200)
        .message("created success")
        .build();
    }

    @PutMapping("/{productId}")
    ApiResponse<Void> updateProduct(
        @RequestBody ProductRequest request,
        @PathVariable Integer productId
    ){
        productService.updateProduct(productId, request);
        return ApiResponse.<Void>builder()
        .code(200)
        .message("updated success")
        .build();
    }
    @DeleteMapping("/{productId}")
    ApiResponse<Void> deleteProduct(@PathVariable Integer productId){
        productService.deleteProduct(productId);
        return ApiResponse.<Void>builder()
        .code(200)
        .message("delete success")
        .build();
    }

    @GetMapping()
    ApiResponse<PageResponse<ProductResponse>> getListProduct(
        @RequestParam(value="search", required =false, defaultValue ="") String search,
        @RequestParam(value="from_date", required = false) Long fromDate,
        @RequestParam(value="to_date", required = false) Long toDate, 
        @RequestParam(value="sort_by", required = false, defaultValue ="productId") String sortBy,
        @RequestParam(value="sort_dir", required = false, defaultValue = "desc") String sortDir,
        @RequestParam(value="page_no", required = false, defaultValue = "1") int pageNo,
        @RequestParam(value="page_size", required = false, defaultValue = "5" ) int pageSize
    ){
        return ApiResponse.<PageResponse<ProductResponse>>builder()
        .code(200)
        .message("get success")
        .result(productService.getAllProducts(search, fromDate, toDate, sortBy, sortDir, pageNo, pageSize))
        .build();
    }
}
