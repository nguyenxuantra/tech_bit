package com.tech_bit.tech_bit.dto.request;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@RequiredArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleRequest {
    String name;
    String description;
    Set<Integer> permissions;
}
