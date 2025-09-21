package com.tech_bit.tech_bit.dto.response;

import java.util.Set;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UAccountResponse {
    private String username;
    private String email;
    private String password;
    private Set<String> roles;
    private Long createdAt;
    private Long updateAt;
}
