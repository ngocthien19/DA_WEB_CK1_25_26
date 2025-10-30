# Cập nhật Đặc tả - Phần Vận chuyển và Giao hàng

## 1. Quản lý Vận chuyển (Shipper)

### 1.1. Quản lý Đơn hàng được phân công
- **Mô tả**: Quy trình xử lý đơn hàng của shipper
- **Tác nhân**: Shipper
- **Điều kiện trước**: 
  - Đăng nhập với role Shipper
  - Được phân công đơn hàng
- **Luồng chính**:
  1. Nhận đơn hàng mới:
     - Xem danh sách đơn được phân công
     - Kiểm tra:
       + Địa chỉ giao hàng
       + Thời gian yêu cầu
       + Phương thức thanh toán
     - Xác nhận nhận đơn
  
  2. Quy trình lấy hàng:
     - Đến địa điểm lấy hàng
     - Xác nhận với cửa hàng:
       + Quét mã QR đơn hàng
       + Kiểm tra sản phẩm
       + Chụp ảnh xác nhận
     - Cập nhật trạng thái "Đã lấy hàng"
  
  3. Quy trình giao hàng:
     - Định tuyến giao hàng tối ưu
     - Liên hệ khách hàng
     - Xác nhận giao hàng:
       + Quét mã QR xác nhận
       + Thu tiền (nếu COD)
       + Chụp ảnh bằng chứng giao
     - Cập nhật trạng thái "Đã giao"

  4. Xử lý ngoại lệ:
     - Khách không nhận hàng:
       + Ghi nhận lý do
       + Chụp ảnh bằng chứng
       + Liên hệ hỗ trợ
     - Hàng bị hư hỏng:
       + Lập biên bản
       + Chụp ảnh thiệt hại
       + Báo cáo sự cố
     - Không liên lạc được:
       + Ghi nhận số lần gọi
       + Chờ theo quy định
       + Báo hỗ trợ xử lý

### 1.2. Theo dõi Hiệu suất Giao hàng
- **Mô tả**: Hệ thống đánh giá hiệu suất shipper
- **Tác nhân**: Shipper, Admin
- **Điều kiện trước**: Có lịch sử giao hàng
- **Luồng chính**:
  1. Thống kê giao hàng:
     - Số lượng đơn:
       + Tổng đơn nhận
       + Đơn giao thành công
       + Đơn giao thất bại
     - Thời gian giao:
       + Thời gian trung bình/đơn
       + Tỷ lệ giao đúng hẹn
       + Thời gian chậm trễ
     - Đánh giá khách hàng:
       + Điểm đánh giá
       + Nhận xét
       + Khiếu nại (nếu có)

  2. Báo cáo chi tiết:
     - Theo ngày/tuần/tháng
     - Phân tích hiệu suất:
       + Thời gian cao điểm
       + Khu vực giao hàng
       + Loại đơn hàng
     - Đề xuất cải thiện

  3. Quản lý thu nhập:
     - Tính phí giao hàng:
       + Phí cơ bản
       + Phụ phí (nếu có)
       + Thưởng giao nhanh
     - Thống kê thu nhập:
       + Thu nhập theo ngày
       + Tổng thu nhập tháng
       + Lịch sử thanh toán

### 1.3. Quản lý Tuyến đường
- **Mô tả**: Tối ưu hóa lộ trình giao hàng
- **Tác nhân**: Shipper
- **Điều kiện trước**: Có đơn hàng cần giao
- **Luồng chính**:
  1. Lập kế hoạch tuyến đường:
     - Xem danh sách đơn hàng
     - Phân tích địa điểm:
       + Vị trí lấy hàng
       + Địa chỉ giao hàng
       + Thời gian yêu cầu
     - Tối ưu lộ trình:
       + Sắp xếp thứ tự giao
       + Tính toán quãng đường
       + Dự kiến thời gian

  2. Theo dõi real-time:
     - Cập nhật vị trí GPS
     - Hiển thị:
       + Vị trí hiện tại
       + Đơn hàng gần nhất
       + Ước tính thời gian
     - Thông báo:
       + Đến giờ lấy hàng
       + Gần đến nơi giao
       + Cảnh báo chậm trễ

  3. Điều chỉnh lộ trình:
     - Xử lý tình huống:
       + Kẹt xe
       + Khách hẹn lại giờ
       + Đơn gấp mới
     - Tự động tối ưu:
       + Tính toán lại đường đi
       + Cập nhật thời gian
       + Thông báo các bên

- **Luồng ngoại lệ**:
  1. Lỗi GPS:
     - Ghi nhận sự cố
     - Chuyển chế độ offline
     - Báo cáo kỹ thuật
  
  2. Thay đổi đột xuất:
     - Hủy đơn gấp
     - Thay đổi địa chỉ
     - Sự cố phương tiện
  
  3. Vùng không hỗ trợ:
     - Thông báo shipper
     - Đề xuất tuyến thay thế
     - Chuyển đơn nếu cần