package com.tech_bit.tech_bit.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.tech_bit.tech_bit.entity.Categories;

import jakarta.persistence.criteria.Predicate;

public class CategorySpecification {
    public static Specification<Categories> searchAllFields(String keyword, Long fromDate, Long toDate){
        return (root, query, cb)->{
            
            
            List<Predicate> predicates = new ArrayList<>();
            if(keyword != null && !keyword.trim().isEmpty()){
                String likePattern = "%" + keyword.toLowerCase() + "%";
                predicates.add(cb.like(cb.lower(root.get("name")), likePattern));
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
