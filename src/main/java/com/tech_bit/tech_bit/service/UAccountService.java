package com.tech_bit.tech_bit.service;

import com.tech_bit.tech_bit.dto.request.UAccountRequest;
import com.tech_bit.tech_bit.dto.request.UAddressRequest;
import com.tech_bit.tech_bit.dto.response.UAccountResponse;

public interface UAccountService {
    UAccountResponse getAccount(Integer userId);

    void updateAccount(Integer userId, UAccountRequest accountRequest);

    void createAddress(UAddressRequest addressRequest, Integer userId);

    void updateAddress(UAddressRequest addressRequest, Integer addressId);

    void deleteAddress(Integer addressId);

    void createAccount(UAccountRequest accountRequest);

    UAccountResponse getMyInfo();
}
