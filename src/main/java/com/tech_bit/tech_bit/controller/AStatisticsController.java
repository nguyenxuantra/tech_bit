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

    /**
     * Lấy thống kê doanh thu theo thời gian
     * @param fromDate Ngày bắt đầu (format: yyyy-MM-dd) - nên truyền vào, nếu không sẽ mặc định là đầu tháng hiện tại
     * @param toDate Ngày kết thúc (format: yyyy-MM-dd) - nên truyền vào, nếu không sẽ mặc định là cuối tháng hiện tại
     * @param groupBy Nhóm theo: DAY | MONTH | YEAR (mặc định: DAY)
     * @return Danh sách thống kê doanh thu theo thời gian
     */
    @GetMapping("/time")
    public ApiResponse<List<TimeStatisticsResponse>> getTimeStatistics(
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
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
