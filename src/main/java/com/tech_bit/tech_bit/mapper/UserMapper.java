package com.tech_bit.tech_bit.mapper;

import com.tech_bit.tech_bit.dto.request.UAccountRequest;
import com.tech_bit.tech_bit.dto.response.UAccountResponse;
import com.tech_bit.tech_bit.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UAccountRequest accountRequest);
    void updateUser(@MappingTarget User user, UAccountRequest accountRequest);

    UAccountResponse toUAccountResponse(User user);
}
