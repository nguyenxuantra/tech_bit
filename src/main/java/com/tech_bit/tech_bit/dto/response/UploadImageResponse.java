package com.tech_bit.tech_bit.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UploadImageResponse {
    private String secureUrl;
    private String publicId;
}
