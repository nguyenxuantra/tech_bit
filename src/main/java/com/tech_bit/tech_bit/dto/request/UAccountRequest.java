package com.tech_bit.tech_bit.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class UAccountRequest {
    private String username;
    private String email;
    private String password;
}
