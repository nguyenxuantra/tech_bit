package com.tech_bit.tech_bit.controller;

import com.tech_bit.tech_bit.common.apiResponse.ApiResponse;
import com.tech_bit.tech_bit.common.pageResponse.PageResponse;
import com.tech_bit.tech_bit.dto.response.ProductResponse;
import com.tech_bit.tech_bit.service.ProductService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductController {
    ProductService productService;

    @GetMapping
    public ApiResponse<PageResponse<ProductResponse>> getProducts(
            @RequestParam(value = "search", required = false, defaultValue = "") String search,
            @RequestParam(value = "category_id", required = false) Integer categoryId,
            @RequestParam(value = "min_price", required = false) Double minPrice,
            @RequestParam(value = "max_price", required = false) Double maxPrice,
            @RequestParam(value = "flash_sale", required = false) Boolean flashSale,
            @RequestParam(value = "sort_by", required = false, defaultValue = "productId") String sortBy,
            @RequestParam(value = "sort_dir", required = false, defaultValue = "desc") String sortDir,
            @RequestParam(value = "page_no", required = false, defaultValue = "1") int pageNo,
            @RequestParam(value = "page_size", required = false, defaultValue = "10") int pageSize
    ) {
        return ApiResponse.<PageResponse<ProductResponse>>builder()
                .code(200)
                .message("Lấy danh sách sản phẩm thành công")
                .result(productService.getProducts(search, categoryId, minPrice, maxPrice, flashSale, sortBy, sortDir, pageNo, pageSize))
                .build();
    }
}
