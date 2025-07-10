package com.tech_bit.tech_bit.controller;

import com.tech_bit.tech_bit.common.apiResponse.ApiResponse;

import com.tech_bit.tech_bit.dto.request.UAccountRequest;
import com.tech_bit.tech_bit.dto.request.UAddressRequest;
import com.tech_bit.tech_bit.dto.response.UAccountResponse;
import com.tech_bit.tech_bit.service.UAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("users")
public class UAccountController {
    @Autowired
    private UAccountService accountService;

    // Tạo user
    @PostMapping
    ApiResponse<Void> createAccount(@RequestBody UAccountRequest accountRequest) {
        accountService.createAccount(accountRequest);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Success")
                .build();
    }
    // Lấy thông tin tài khoản
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    ApiResponse<UAccountResponse> getAccount(@PathVariable Integer userId) {

        return ApiResponse.<UAccountResponse>builder()
                .code(200)
                .message("Success")
                .result(accountService.getAccount(userId))
                .build();
    }
    // Cập nhật thông tin tài khoản
    @PutMapping("/{userId}")
    ApiResponse<String> updateAccount(@PathVariable Integer userId){
        accountService.updateAccount(userId, null);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Success")
                .build();
    }

    // Tạo mới địa chỉ
    @PostMapping("/{userId}/address")
    ApiResponse<String> createAddress(@PathVariable Integer userId,
                                      @RequestBody UAddressRequest addressRequest){
        accountService.createAddress(addressRequest, userId);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Success")
                .build();
    }

    // Sửa địa chỉ
    @PutMapping("/address")
    ApiResponse<String> updateAddress(@RequestParam Integer addressId,
                                      @RequestBody UAddressRequest addressRequest){
        accountService.updateAddress(addressRequest, addressId);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Success")
                .build();
    }
    // Xóa địa chỉ
    @DeleteMapping("/address")
    ApiResponse<String> deleteAddress(@RequestParam Integer addressId){
        accountService.deleteAddress(addressId);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Success")
                .build();
    }

    @GetMapping("/me")
    ApiResponse<UAccountResponse> getMyInfo(){
        return ApiResponse.<UAccountResponse>builder()
                .code(200)
                .message("Success")
                .result(accountService.getMyInfo())
                .build();
    }


}
