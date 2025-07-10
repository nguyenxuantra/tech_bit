package com.tech_bit.tech_bit.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UAccountResponse {
    private String username;
    private String email;
    private String password;
    private String role;
    private Long createdAt;
    private Long updateAt;
}
