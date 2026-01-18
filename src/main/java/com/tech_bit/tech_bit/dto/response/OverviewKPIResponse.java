package com.tech_bit.tech_bit.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OverviewKPIResponse {
    Long totalUsers;
    Long totalOrders;
    Long totalRevenue;
    Long totalProducts;
    Long newUsersToday;
    Long ordersToday;
    Long revenueToday;
}
