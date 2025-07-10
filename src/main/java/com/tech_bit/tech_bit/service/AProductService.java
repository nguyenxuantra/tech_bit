package com.tech_bit.tech_bit.service;

import com.tech_bit.tech_bit.dto.request.ProductRequest;
import com.tech_bit.tech_bit.entity.Product;
import com.tech_bit.tech_bit.exception.AppException;
import com.tech_bit.tech_bit.exception.ErrorCode;
import com.tech_bit.tech_bit.mapper.ProductMapper;
import com.tech_bit.tech_bit.repository.ProductRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AProductService implements AProductServiceImpl{
    ProductRepository productRepository;
    ProductMapper  productMapper;

    @Override
    public void createProduct(ProductRequest request){
        Product product = productMapper.toProduct(request);
        productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts(){
        return productRepository.findAll();
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
