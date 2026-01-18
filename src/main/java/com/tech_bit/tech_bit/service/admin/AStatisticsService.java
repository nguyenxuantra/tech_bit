package com.tech_bit.tech_bit.service.admin;

import com.tech_bit.tech_bit.dto.response.*;
import java.util.List;

public interface AStatisticsService {
    OverviewKPIResponse getOverviewKPI();
    List<TimeStatisticsResponse> getTimeStatistics(String fromDate, String toDate, String groupBy);
    OrderStatisticsResponse getOrderStatistics();
    List<TopProductResponse> getTopProducts();
}
