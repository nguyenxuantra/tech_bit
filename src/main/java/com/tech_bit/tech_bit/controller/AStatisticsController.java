package com.tech_bit.tech_bit.controller;

import com.tech_bit.tech_bit.common.apiResponse.ApiResponse;
import com.tech_bit.tech_bit.dto.response.*;
import com.tech_bit.tech_bit.service.admin.AStatisticsService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/statistics")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@PreAuthorize("hasRole('ADMIN')")
public class AStatisticsController {

    AStatisticsService statisticsService;

    @GetMapping("/overview")
    public ApiResponse<OverviewKPIResponse> getOverviewKPI() {
        return ApiResponse.<OverviewKPIResponse>builder()
                .code(200)
                .message("Lấy KPI tổng quan thành công")
                .result(statisticsService.getOverviewKPI())
                .build();
    }

    @GetMapping("/time")
    public ApiResponse<List<TimeStatisticsResponse>> getTimeStatistics(
            @RequestParam("fromDate") String fromDate,
            @RequestParam("toDate") String toDate,
            @RequestParam(value = "groupBy", defaultValue = "DAY") String groupBy
    ) {
        return ApiResponse.<List<TimeStatisticsResponse>>builder()
                .code(200)
                .message("Lấy thống kê theo thời gian thành công")
                .result(statisticsService.getTimeStatistics(fromDate, toDate, groupBy))
                .build();
    }

    @GetMapping("/orders")
    public ApiResponse<OrderStatisticsResponse> getOrderStatistics() {
        return ApiResponse.<OrderStatisticsResponse>builder()
                .code(200)
                .message("Lấy thống kê đơn hàng thành công")
                .result(statisticsService.getOrderStatistics())
                .build();
    }

    @GetMapping("/top-products")
    public ApiResponse<List<TopProductResponse>> getTopProducts() {
        return ApiResponse.<List<TopProductResponse>>builder()
                .code(200)
                .message("Lấy top sản phẩm thành công")
                .result(statisticsService.getTopProducts())
                .build();
    }
}
