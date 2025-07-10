package com.tech_bit.tech_bit.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UAddressRequest {
    private String street;
    private String city;
    private String country;
    private String phone;
    private boolean isDefault;
}
