package com.tech_bit.tech_bit.service;

import com.tech_bit.tech_bit.common.pageResponse.PageResponse;
import com.tech_bit.tech_bit.dto.response.AAccountResponse;

public interface AAccountService {
    PageResponse<AAccountResponse> listUser(String search, Long fromDate, Long toDate, String sortBy, String sortDir, int page, int size);
}