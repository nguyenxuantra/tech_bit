package com.tech_bit.tech_bit.repository;

import com.tech_bit.tech_bit.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {
    @Query("SELECT COUNT(p) FROM Product p WHERE p.categories.categoryId = :categoryId")
    long countByCategoryId(@Param("categoryId") Integer categoryId);
}
