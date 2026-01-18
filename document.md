




yêu cầu: @GetMapping("/time")
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
api này nếu người dùng không truyền date vào thì mặc định lấy thời gian tháng hiện tại cho tôi và comment kiểu dạng fromdate và todate người dùng phải truyền vào 


chú ý : nếu api không đủ điều kiện thì không cần tạo thêm entity

