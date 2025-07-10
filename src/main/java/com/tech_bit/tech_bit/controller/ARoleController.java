package com.tech_bit.tech_bit.controller;

import com.tech_bit.tech_bit.common.apiResponse.ApiResponse;
import com.tech_bit.tech_bit.dto.request.RoleRequest;
import com.tech_bit.tech_bit.dto.response.RoleResponse;
import com.tech_bit.tech_bit.service.ARoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/role")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class ARoleController {
    ARoleService roleService;

    @PostMapping
    ApiResponse<RoleResponse> createRole(
            @RequestBody RoleRequest request
    ){
        roleService.create(request);
        return ApiResponse.<RoleResponse>builder()
                .code(200)
                .message("Role created")
                .build();
    }

    @DeleteMapping("/{roleId}")
    ApiResponse<Void> deleteRole(@PathVariable Integer roleId){
        roleService.deleteRole(roleId);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Role deleted")
                .build();
    }
}
