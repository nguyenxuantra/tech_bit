package com.tech_bit.tech_bit.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ACategoryRequest {
    private String name;
    private String imageUrl;
}
