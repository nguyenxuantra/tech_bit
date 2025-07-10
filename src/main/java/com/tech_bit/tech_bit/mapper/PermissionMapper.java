package com.tech_bit.tech_bit.mapper;

import com.tech_bit.tech_bit.dto.request.APermissionRequest;
import com.tech_bit.tech_bit.dto.response.APermissionResponse;
import com.tech_bit.tech_bit.entity.Permission;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;


@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(APermissionRequest request);
    void putPermission(@MappingTarget Permission permission, APermissionRequest permissionRequest);
    APermissionResponse toAPermissionResponse(APermissionRequest request);
}
