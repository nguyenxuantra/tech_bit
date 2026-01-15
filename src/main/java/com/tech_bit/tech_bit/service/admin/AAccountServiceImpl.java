 package com.tech_bit.tech_bit.service.admin;


 import java.util.List;

 import org.springframework.data.domain.Page;
 import org.springframework.data.domain.PageRequest;
 import org.springframework.data.domain.Pageable;
 import org.springframework.data.domain.Sort;
 import org.springframework.stereotype.Service;

 import com.tech_bit.tech_bit.common.pageResponse.PageResponse;
 import com.tech_bit.tech_bit.dto.response.AAccountResponse;
 import com.tech_bit.tech_bit.entity.User;
 import com.tech_bit.tech_bit.exception.AppException;
 import com.tech_bit.tech_bit.exception.ErrorCode;
 import com.tech_bit.tech_bit.repository.OrderRepository;
 import com.tech_bit.tech_bit.repository.UserRepository;
 import com.tech_bit.tech_bit.specification.AccountSpecification;

 import lombok.AccessLevel;
 import lombok.RequiredArgsConstructor;
 import lombok.experimental.FieldDefaults;

 @Service
 @RequiredArgsConstructor
 @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
 public class AAccountServiceImpl implements AAccountService {
     UserRepository userRepository;
     OrderRepository orderRepository;
    
     @Override
      public PageResponse<AAccountResponse> listUser(String search, Long fromDate, Long toDate, String sortBy, String sortDir, int page, int size) {
         Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
         Pageable pageable = PageRequest.of(page - 1, size, sort);
         Page<User> listUser = userRepository.findAll(AccountSpecification.searchAllFields(search, fromDate, toDate), pageable);
         List<AAccountResponse> listUserResponse = listUser.stream().map(user->{
            List<com.tech_bit.tech_bit.entity.Order> orders = orderRepository.findByUserId(user.getUserId());
            int totalOrder = orders.size();
            double totalSpending = orders.stream()
                .mapToDouble(order -> order.getTotalAmount() != null ? order.getTotalAmount() : 0.0)
                .sum();
            
            return AAccountResponse.builder()
            .userId(user.getUserId())
            .username(user.getUsername())
            .email(user.getEmail())
            .totalOrder(totalOrder)
            .totalSpending((int) totalSpending)
            .createdAt(user.getCreatedAt())
            .build();
         }).toList();
         return PageResponse.<AAccountResponse>builder()
         .content(listUserResponse)
         .pageNo(listUser.getNumber())
         .pageSize(listUser.getSize())
         .totalElement(listUser.getTotalElements())
         .totalPages(listUser.getTotalPages())
         .last(listUser.isLast())
         .build();
     }

     @Override
     public AAccountResponse getUserDetail(Integer userId) {
         User user = userRepository.findById(userId)
             .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
         
         List<com.tech_bit.tech_bit.entity.Order> orders = orderRepository.findByUserId(userId);
         int totalOrder = orders.size();
         double totalSpending = orders.stream()
             .mapToDouble(order -> order.getTotalAmount() != null ? order.getTotalAmount() : 0.0)
             .sum();
         
         return AAccountResponse.builder()
             .userId(user.getUserId())
             .username(user.getUsername())
             .email(user.getEmail())
             .totalOrder(totalOrder)
             .totalSpending((int) totalSpending)
             .createdAt(user.getCreatedAt())
             .build();
     }
    
 }
