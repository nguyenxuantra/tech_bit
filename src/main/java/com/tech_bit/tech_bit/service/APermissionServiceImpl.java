package com.tech_bit.tech_bit.service;


import com.tech_bit.tech_bit.dto.request.APermissionRequest;
import com.tech_bit.tech_bit.dto.response.APermissionResponse;
import com.tech_bit.tech_bit.entity.Permission;
import com.tech_bit.tech_bit.exception.AppException;
import com.tech_bit.tech_bit.exception.ErrorCode;
import com.tech_bit.tech_bit.mapper.PermissionMapper;
import com.tech_bit.tech_bit.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class APermissionServiceImpl implements APermissionService{

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;


    @Override
    public void createPermission(APermissionRequest permissionRequest){
        Permission permission = new Permission();
        permission.setDescription(permissionRequest.getDescription());
        permission.setName(permissionRequest.getName());
        permissionRepository.save(permission);
    }

    @Override
    public List<Permission> getPermissions(){
        return permissionRepository.findAll();
    }

    @Override
    public void deletePermission(Integer permissionId){
        permissionRepository.deleteById(permissionId);
    }

    @Override
    public APermissionResponse putPermission(Integer permissionId, APermissionRequest permissionRequest){
        Permission permission = permissionRepository.findById(permissionId).orElseThrow(()-> new AppException(ErrorCode.PERMISSION_NOT_FOUND));
        permission.setName(permissionRequest.getName());
        permission.setDescription(permissionRequest.getDescription());
        permissionRepository.save(permission);
        return permissionMapper.toAPermissionResponse(permissionRequest);
    }
}
