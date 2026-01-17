package com.tech_bit.tech_bit.service;

import com.tech_bit.tech_bit.common.pageResponse.PageResponse;
import com.tech_bit.tech_bit.dto.response.ProductResponse;
import com.tech_bit.tech_bit.entity.Product;
import com.tech_bit.tech_bit.repository.ProductRepository;
import com.tech_bit.tech_bit.specification.ProductSpecification;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ProductServiceImpl implements ProductService {
    ProductRepository productRepository;

    @Override
    public PageResponse<ProductResponse> getProducts(String search, Integer categoryId, Double minPrice, Double maxPrice, Boolean flashSale, String sortBy, String sortDir, int page, int size) {
        String sortByNew = sortBy.equalsIgnoreCase("categoryName") ? "categories.name" : sortBy;
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortByNew).descending() : Sort.by(sortByNew).ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        Page<Product> products = productRepository.findAll(
                ProductSpecification.searchForUser(search, categoryId, minPrice, maxPrice, flashSale), 
                pageable);
        
        List<ProductResponse> productResult = products.stream().map(product -> {
            String categoryName = product.getCategories().getName();
            return ProductResponse.builder()
                    .productId(product.getProductId())
                    .name(product.getName())
                    .description(product.getDescription())
                    .price(product.getPrice())
                    .rating(product.getRating())
                    .discount(product.getDiscount())
                    .stock(product.getStock())
                    .imageUrl(product.getImageUrl())
                    .brand(product.getBrand())
                    .categoryName(categoryName)
                    .createdAt(product.getCreatedAt())
                    .build();
        }).toList();

        return PageResponse.<ProductResponse>builder()
                .content(productResult)
                .pageNo(products.getNumber())
                .pageSize(products.getSize())
                .totalPages(products.getTotalPages())
                .totalElement(products.getTotalElements())
                .last(products.isLast())
                .build();
    }
}
