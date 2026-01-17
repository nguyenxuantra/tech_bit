package com.tech_bit.tech_bit.mapper;

import com.tech_bit.tech_bit.dto.request.UAccountRequest;
import com.tech_bit.tech_bit.dto.response.UAccountResponse;
import com.tech_bit.tech_bit.entity.Users;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")

public interface UserMapper {
    Users toUser(UAccountRequest accountRequest);
    void updateUser(@MappingTarget Users user, UAccountRequest accountRequest);
    UAccountResponse toUAccountResponse(Users user);
}
