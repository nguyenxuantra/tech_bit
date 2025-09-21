// package com.tech_bit.tech_bit.service;


// import java.util.List;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.PageRequest;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.domain.Sort;
// import org.springframework.stereotype.Service;

// import com.fasterxml.jackson.annotation.JsonProperty.Access;
// import com.tech_bit.tech_bit.common.pageResponse.PageResponse;
// import com.tech_bit.tech_bit.dto.response.AAccountResponse;
// import com.tech_bit.tech_bit.entity.User;
// import com.tech_bit.tech_bit.repository.UserRepository;

// import lombok.AccessLevel;
// import lombok.RequiredArgsConstructor;
// import lombok.experimental.FieldDefaults;

// @Service
// @RequiredArgsConstructor
// @FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
// public class AAccountServiceImpl implements AAccountService{
//     UserRepository userRepository;  
    
//     @Override
//     PageResponse<AAccountResponse> listUser(String search, Long fromDate, Long toDate, String sortBy, String sortDir, int page, int size) {
//         Sort sort = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
//         Pageable pageable = PageRequest.of(page - 1, size, sort);
//         Page<User> listUser = userRepository.findAll(pageable);
//         List<AAccountResponse> listUserResponse = listUser.stream().map(user->{
//            return AAccountResponse.builder()
//            .userId(user.getUserId())
//            .username(user.getUsername())
//            .email(user.getEmail())
//            .createdAt(user.getCreatedAt())
//            .status(user.getStatus())
//            .build(); 
//         }).toList();
//         return PageResponse.<AAccountResponse>builder()
//         .content(listUserResponse)
//         .pageNo(listUser.getNumber())
//         .pageSize(listUser.getSize())
//         .totalElement(listUser.getTotalElements())
//         .totalPages(listUser.getTotalPages())
//         .last(listUser.isLast())
//         .build();
//     }
    
// }
