package com.tech_bit.tech_bit.service;


import com.tech_bit.tech_bit.dto.request.RoleRequest;
import com.tech_bit.tech_bit.dto.response.RoleResponse;
import com.tech_bit.tech_bit.entity.Permission;
import com.tech_bit.tech_bit.entity.Role;
import com.tech_bit.tech_bit.entity.RolePermission;
import com.tech_bit.tech_bit.entity.RolePermissionId;
import com.tech_bit.tech_bit.mapper.RoleMapper;
import com.tech_bit.tech_bit.repository.PermissionRepository;
import com.tech_bit.tech_bit.repository.RolePermissionRepository;
import com.tech_bit.tech_bit.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ARoleServiceImpl implements ARoleService{
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;
    RolePermissionRepository rolePermissionRepository;


    public void create(RoleRequest request){
        Role role = roleMapper.toRole(request);
        List<Permission> listPermissions = permissionRepository.findAllById(request.getPermissions());
        role = roleRepository.save(role);
        final Integer roleId = role.getId();
        List<RolePermission> rolePermissions = listPermissions.stream().map(pm->{
            RolePermission rp = new RolePermission();
            rp.setId(new RolePermissionId(roleId, pm.getId()));
            return rp;
        }).toList();
        rolePermissionRepository.saveAll(rolePermissions);
    }

    public void deleteRole(Integer roleId){
        roleRepository.deleteById(roleId);
    }

}
