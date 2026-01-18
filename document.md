




yêu cầu: viết những api sau cho admin 
- api API KPI tổng quan
data trả về 
{
  "totalUsers": 1250,
  "totalOrders": 8420,
  "totalRevenue": 185000000,
  "totalProducts": 320,
  "newUsersToday": 12,
  "ordersToday": 48,
  "revenueToday": 9500000
}

- API thống kê theo thời gian
+ query param
?fromDate=2025-01-01
&toDate=2025-01-31
&groupBy=DAY | MONTH | YEAR
+ data trả về 
[
  { "date": "2025-01-01", "revenue": 5000000 },
  { "date": "2025-01-02", "revenue": 7200000 },
  { "date": "2025-01-03", "revenue": 6300000 }
]
API thống kê số đơn hàng 
data trả về 
{
  "total": 8420,
  "success": 7890,
  "approved": 320,
  "pending": 210
}
api API top sản phẩm
data trả vè 
[
  { "productId": 1, "name": "iPhone 15", "quantity": 320, "revenue": 96000000 },
  { "productId": 2, "name": "Samsung S24", "quantity": 280, "revenue": 78000000 }
]


chú ý : nếu api không đủ điều kiện thì không cần tạo thêm entity

