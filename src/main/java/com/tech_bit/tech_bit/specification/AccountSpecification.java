// package com.tech_bit.tech_bit.specification;

// import java.util.ArrayList;
// import java.util.List;

// import org.springframework.data.jpa.domain.Specification;

// import com.tech_bit.tech_bit.entity.User;

// import jakarta.persistence.criteria.Predicate;

// public class AccountSpecification {
//     public static Specification<User> searchAllfields(String keyword, Long fromDate, Long toDate){
//         return (root, query, cb)->{
//             List<Predicate> predicates = new ArrayList<>();
//             if(keyword != null && !keyword.isEmpty()){
//                 String likePattern = "%" + keyword.toLowerCase() + "%";
//                 predicates.add(cb.like(cb.lower(root.get())))
//             }
//         }
//     }
// }
