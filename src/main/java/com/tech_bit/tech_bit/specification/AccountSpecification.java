package com.tech_bit.tech_bit.specification;

import java.util.ArrayList;
import java.util.List;

import com.tech_bit.tech_bit.entity.Users;
import org.springframework.data.jpa.domain.Specification;

import jakarta.persistence.criteria.Predicate;

public class AccountSpecification {
    public static Specification<Users> searchAllFields(String keyword, Long fromDate, Long toDate){
        return (root, query, cb)->{
            List<Predicate> predicates = new ArrayList<>();
            if(keyword != null && !keyword.trim().isEmpty()){
                String likePattern = "%" + keyword.toLowerCase() + "%";
                predicates.add(cb.or(
                    cb.like(cb.lower(root.get("username")), likePattern),
                    cb.like(cb.lower(root.get("email")), likePattern)
                ));
            }
            if(fromDate != null){
                predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), fromDate));
            }
            if(toDate != null){
                predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), toDate));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
