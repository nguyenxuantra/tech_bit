package com.tech_bit.tech_bit.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ACategoryResponse {
    private Integer categoryId;
    private String name;
    private String imageUrl;
}
