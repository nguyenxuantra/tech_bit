package com.tech_bit.tech_bit.mapper;

import com.tech_bit.tech_bit.dto.request.RoleRequest;
import com.tech_bit.tech_bit.dto.response.RoleResponse;

import com.tech_bit.tech_bit.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleRequest request);
}
