package com.tech_bit.tech_bit.controller;

import com.tech_bit.tech_bit.common.apiResponse.ApiResponse;
import com.tech_bit.tech_bit.dto.request.APermissionRequest;
import com.tech_bit.tech_bit.dto.response.APermissionResponse;
import com.tech_bit.tech_bit.entity.Permission;
import com.tech_bit.tech_bit.service.APermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/admin/permission")
public class APermissionController {
    APermissionService permissionService;

    @PostMapping
    ApiResponse<String> createPermission(@RequestBody APermissionRequest permissionRequest) {
        permissionService.createPermission(permissionRequest);
        return ApiResponse.<String>builder()
                .code(200)
                .message("Permission created")
                .build();
    }

    @GetMapping
    ApiResponse<List<Permission>> getPermissions(){
        return ApiResponse.<List<Permission>>builder()
                .code(200)
                .message("success")
                .result(permissionService.getPermissions())
                .build();

    }
    @DeleteMapping("/{permissionId}")
    ApiResponse<Void> deletePermission(@PathVariable Integer permissionId){
        permissionService.deletePermission(permissionId);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Delete success")
                .build();

    }

    @PutMapping("/{permissionId}")
    ApiResponse<APermissionResponse> putPermission(@PathVariable Integer permissionId,
                                                   @RequestBody APermissionRequest permissionRequest){
        return ApiResponse.<APermissionResponse>builder()
                .code(200)
                .message("Put success")
                .result(permissionService.putPermission(permissionId, permissionRequest))
                .build();
    }
}
