package com.tech_bit.tech_bit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tech_bit.tech_bit.common.apiResponse.ApiResponse;
import com.tech_bit.tech_bit.common.pageResponse.PageResponse;
import com.tech_bit.tech_bit.dto.response.AAccountResponse;
import com.tech_bit.tech_bit.service.admin.AAccountService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/admin/account")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AAccountController {
    AAccountService accountService;

    @GetMapping
    public ApiResponse<PageResponse<AAccountResponse>> getListUsers(
        @RequestParam(value = "search", required = false, defaultValue = "") String search,
        @RequestParam(value = "from_date", required = false) Long fromDate,
        @RequestParam(value = "to_date", required = false) Long toDate,
        @RequestParam(value = "sort_by", required = false, defaultValue = "userId") String sortBy,
        @RequestParam(value = "sort_dir", required = false, defaultValue = "desc") String sortDir,
        @RequestParam(value = "page_no", required = false, defaultValue = "1") int pageNo,
        @RequestParam(value = "page_size", required = false, defaultValue = "5") int pageSize
    ) {
        return ApiResponse.<PageResponse<AAccountResponse>>builder()
            .code(200)
            .message("Lấy danh sách users thành công")
            .result(accountService.listUser(search, fromDate, toDate, sortBy, sortDir, pageNo, pageSize))
            .build();
    }

    @GetMapping("/{userId}")
    public ApiResponse<AAccountResponse> getUserDetail(@PathVariable Integer userId) {
        return ApiResponse.<AAccountResponse>builder()
            .code(200)
            .message("Lấy thông tin user thành công")
            .result(accountService.getUserDetail(userId))
            .build();
    }
}
