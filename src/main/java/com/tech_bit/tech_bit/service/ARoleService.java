package com.tech_bit.tech_bit.service;

import com.tech_bit.tech_bit.dto.request.RoleRequest;
import com.tech_bit.tech_bit.dto.response.RoleResponse;

import java.util.List;

public interface ARoleService {
    void create(RoleRequest request);
    void deleteRole(Integer roleId);
}
