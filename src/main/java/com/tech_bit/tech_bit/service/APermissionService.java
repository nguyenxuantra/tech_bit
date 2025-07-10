package com.tech_bit.tech_bit.service;

import com.tech_bit.tech_bit.dto.request.APermissionRequest;
import com.tech_bit.tech_bit.dto.response.APermissionResponse;
import com.tech_bit.tech_bit.entity.Permission;

import java.util.List;

public interface APermissionService {
    void createPermission(APermissionRequest permissionRequest);
    List<Permission> getPermissions();
    void deletePermission(Integer permissionId);
    APermissionResponse putPermission(Integer permissionId, APermissionRequest permissionRequest);
}
