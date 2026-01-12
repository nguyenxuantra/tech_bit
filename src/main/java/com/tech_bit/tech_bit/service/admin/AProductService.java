package com.tech_bit.tech_bit.service.admin;

import com.tech_bit.tech_bit.common.pageResponse.PageResponse;
import com.tech_bit.tech_bit.dto.request.ProductRequest;
import com.tech_bit.tech_bit.dto.response.ProductResponse;
import com.tech_bit.tech_bit.entity.Categories;
import com.tech_bit.tech_bit.entity.Product;
import com.tech_bit.tech_bit.exception.AppException;
import com.tech_bit.tech_bit.exception.ErrorCode;
import com.tech_bit.tech_bit.mapper.ProductMapper;
import com.tech_bit.tech_bit.repository.CategoriesRepository;
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
public class AProductService implements AProductServiceImpl {
    ProductRepository productRepository;
    ProductMapper  productMapper;
    CategoriesRepository categoryRepository;

    @Override
    public void createProduct(ProductRequest request){
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setDiscount(request.getDiscount());
        product.setStock(request.getStock());
        product.setBrand(request.getBrand());
        Categories category = categoryRepository.findById(request.getCategoryId()).orElseThrow(()-> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
        product.setCategories(category);
        productRepository.save(product);
    }

    @Override
    public PageResponse<ProductResponse> getAllProducts(String search, Long fromDate, Long toDate, String sortBy, String sortDir, int page, int size){
        String sortByNew = sortBy.equalsIgnoreCase("categoryName" ) ?  "categories.name" : sortBy;
        Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortByNew).descending() : Sort.by(sortByNew).ascending();
        Pageable pageable = PageRequest.of(page-1, size, sort);
        Page<Product> products =  productRepository.findAll(ProductSpecification.searchAllFields(search, fromDate, toDate),pageable);
        List<ProductResponse> productResult = products.stream().map(product ->{
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

        return  PageResponse.<ProductResponse>builder()
        .content(productResult)
        .pageNo(products.getNumber())
        .pageSize(products.getSize())
        .totalPages(products.getTotalPages())
        .totalElement(products.getTotalElements())
        .last(products.isLast())
        .build();
    }

    @Override
    public void deleteProduct(Integer productId){
        productRepository.deleteById(productId);
    }

    @Override
    public void updateProduct(Integer productId, ProductRequest request ){
        Product product = productRepository.findById(productId).orElseThrow(()->new AppException(ErrorCode.PRODUCT_NOT_FOUND));
        productMapper.updateProduct(product, request);
        productRepository.save(product);
    }
}
