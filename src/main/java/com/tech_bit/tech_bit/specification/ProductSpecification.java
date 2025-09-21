package com.tech_bit.tech_bit.specification;

import java.security.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.tech_bit.tech_bit.entity.Categories;
import com.tech_bit.tech_bit.entity.Product;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class ProductSpecification {
    public static Specification<Product> searchAllFields(String keyword, Long fromDate, Long toDate){
        return (root, query, cb)->{
            
            
            List<Predicate> predicates = new ArrayList<>();
            Join<Product, Categories> category = root.join("categories", JoinType.LEFT);
            if(keyword != null && !keyword.trim().isEmpty()){
                String likePattern = "%" + keyword.toLowerCase() + "%";
                predicates.add(cb.or(cb.like(cb.lower(root.get("name")), likePattern),
                                       cb.like(cb.lower(cb.concat(root.get("price").as(String.class),"")), likePattern),
                                       cb.like(cb.lower(root.get("description")), likePattern),
                                       cb.like(cb.lower(cb.concat(root.get("discount").as(String.class),"") ), likePattern),
                                       cb.like(cb.lower(cb.concat(root.get("rating").as(String.class),"") ), likePattern),
                                       cb.like(cb.lower(cb.concat(root.get("stock").as(String.class),"") ), likePattern),
                                       cb.like(cb.lower(root.get("brand")), likePattern),
                                       cb.like(cb.lower(category.get("name")), likePattern)
                                       ));
            }
            if(fromDate != null){
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), fromDate));
            }
            if(toDate !=null){
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), toDate));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
