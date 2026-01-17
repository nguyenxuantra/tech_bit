package com.tech_bit.tech_bit.service;


import com.tech_bit.tech_bit.dto.request.UAccountRequest;
import com.tech_bit.tech_bit.dto.request.UAddressRequest;
import com.tech_bit.tech_bit.dto.response.UAccountResponse;
import com.tech_bit.tech_bit.entity.Addresses;
import com.tech_bit.tech_bit.entity.Users;
import com.tech_bit.tech_bit.enums.Role;
import com.tech_bit.tech_bit.exception.AppException;
import com.tech_bit.tech_bit.exception.ErrorCode;
import com.tech_bit.tech_bit.mapper.UserMapper;
import com.tech_bit.tech_bit.repository.AddressesRepository;
import com.tech_bit.tech_bit.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UAccountServiceImpl implements UAccountService {

    UserRepository userRepository;
    AddressesRepository addressesRepository;
    UserMapper userMapper;

    @Override
    public void createAccount(UAccountRequest accountRequest) {
        Users user = userMapper.toUser(accountRequest);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());
        user.setRoles(roles);
        userRepository.save(user);
    }

    @Override
    public UAccountResponse getAccount(Integer userId) {
        return userMapper.toUAccountResponse(userRepository.findById(userId).orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND)));
    }

    @Override
    public void updateAccount(Integer userId, UAccountRequest accountRequest) {
        Users user = userRepository.findById(userId).orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
        userMapper.updateUser(user, accountRequest);
        userRepository.save(user);
    }

    @Override
    public void createAddress(UAddressRequest addressRequest, Integer userId){
        Addresses addresses = new Addresses();
        addresses.setUserId(userId);
        addresses.setCity(addressRequest.getCity());
        addresses.setCountry(addresses.getCountry());
        addresses.setStreet(addressRequest.getStreet());
        addresses.setDefault(addressRequest.isDefault());
        addresses.setPhone(addressRequest.getPhone());
        addressesRepository.save(addresses);
    }

    @Override
    public void updateAddress(UAddressRequest addressRequest, Integer addressId){
        Addresses address = addressesRepository.findById(addressId).orElseThrow(()->new AppException(ErrorCode.ADDRESS_NOT_FOUND));
        address.setDefault(addressRequest.isDefault());
        address.setPhone(addressRequest.getPhone());
        address.setCity(address.getCity());
        address.setCountry(address.getCountry());
        address.setStreet(address.getStreet());
        addressesRepository.save(address);
    }

    @Override
    public void deleteAddress(Integer addressId) {
        Addresses addresses = addressesRepository.findById(addressId).orElseThrow(()->new AppException(ErrorCode.ADDRESS_NOT_FOUND));
        addressesRepository.delete(addresses);
    }

    @Override
    public UAccountResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        Users user = userRepository.findByUsername(name).orElseThrow(()->new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUAccountResponse(user);
    }
}
