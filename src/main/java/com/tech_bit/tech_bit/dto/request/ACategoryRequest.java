package com.tech_bit.tech_bit.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ACategoryRequest {
    private String name;
    private String imageUrl;
}
