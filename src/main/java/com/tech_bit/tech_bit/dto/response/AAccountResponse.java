package com.tech_bit.tech_bit.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AAccountResponse {
    Integer userId;
    String username;
    String email;
    Integer totalOrder;
    Integer totalSpending;
    Long createdAt;
    Boolean status;
}
