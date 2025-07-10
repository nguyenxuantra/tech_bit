package com.tech_bit.tech_bit.dto.response;


import com.tech_bit.tech_bit.entity.Permission;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleResponse {
    String name;
    Set<APermissionResponse> permissions;
}
