# Cập nhật Đặc tả - Phần Hệ thống Chat và Hỗ trợ

## 1. Hệ thống Chat 

### 1.1. Chat giữa User và Vendor
- **Mô tả**: Hệ thống tin nhắn trực tiếp giữa khách hàng và cửa hàng
- **Tác nhân**: User, Vendor
- **Điều kiện trước**: Đã đăng nhập
- **Luồng chính**:
  1. Khởi tạo cuộc trò chuyện:
     - User:
       + Chọn cửa hàng cần liên hệ
       + Xem thông tin cửa hàng
       + Bắt đầu chat mới
     - Vendor:
       + Xem danh sách chat đến
       + Phân loại theo mức độ ưu tiên
       + Tiếp nhận cuộc trò chuyện

  2. Trao đổi tin nhắn:
     - Gửi tin nhắn:
       + Soạn nội dung
       + Đính kèm file/hình ảnh
       + Gửi link sản phẩm
     - Nhận tin nhắn:
       + Thông báo tin mới
       + Đánh dấu đã đọc
       + Xem lịch sử chat
     - Tính năng nâng cao:
       + Trích dẫn tin nhắn
       + Gửi emoji/sticker
       + Phản hồi nhanh

  3. Quản lý chat:
     - Tìm kiếm tin nhắn:
       + Theo từ khóa
       + Theo thời gian
       + Theo loại file
     - Phân loại hội thoại:
       + Đánh dấu quan trọng
       + Lọc theo trạng thái
       + Lưu trữ/xóa chat

### 1.2. Chat với Shipper
- **Mô tả**: Liên lạc giữa khách hàng và shipper trong quá trình giao hàng
- **Tác nhân**: User, Shipper
- **Điều kiện trước**: Có đơn hàng đang giao
- **Luồng chính**:
  1. Kết nối chat:
     - Tự động khi:
       + Shipper nhận đơn
       + Bắt đầu giao hàng
     - Thông tin hiển thị:
       + Mã đơn hàng
       + Thông tin shipper
       + Trạng thái giao hàng

  2. Tính năng chuyên biệt:
     - Chia sẻ vị trí:
       + Vị trí realtime
       + Ước tính thời gian
       + Thông báo gần đến
     - Cập nhật trạng thái:
       + Thông báo delay
       + Xác nhận đã giao
       + Báo cáo sự cố

  3. Kết thúc chat:
     - Tự động sau:
       + Giao hàng thành công
       + Đơn hàng bị hủy
     - Lưu trữ lịch sử:
       + Nội dung trao đổi
       + Thời gian giao tiếp
       + File đính kèm

### 1.3. Hệ thống Hỗ trợ
- **Mô tả**: Hệ thống hỗ trợ khách hàng tập trung
- **Tác nhân**: User, Admin, Vendor
- **Điều kiện trước**: Đăng nhập hệ thống
- **Luồng chính**:
  1. Tiếp nhận yêu cầu:
     - Phân loại vấn đề:
       + Đơn hàng
       + Thanh toán
       + Kỹ thuật
       + Khiếu nại
     - Mức độ ưu tiên:
       + Khẩn cấp
       + Cao
       + Trung bình
       + Thấp

  2. Xử lý hỗ trợ:
     - Phân công xử lý:
       + Chuyển đúng bộ phận
       + Theo dõi tiến độ
       + Nhắc nhở deadline
     - Tương tác:
       + Chat trực tiếp
       + Gọi điện
       + Email
     - Giải quyết:
       + Hướng dẫn chi tiết
       + Xử lý khiếu nại
       + Bồi thường (nếu có)

  3. Theo dõi và đánh giá:
     - Đánh giá chất lượng:
       + Mức độ hài lòng
       + Tốc độ xử lý
       + Hiệu quả giải quyết
     - Báo cáo thống kê:
       + Số lượng ticket
       + Thời gian xử lý
       + Tỷ lệ giải quyết

- **Luồng ngoại lệ**:
  1. Lỗi kết nối:
     - Lưu tin nhắn offline
     - Gửi lại tự động
     - Thông báo cho user

  2. File không hợp lệ:
     - Kiểm tra định dạng
     - Giới hạn dung lượng
     - Quét virus/malware

  3. Spam/Lạm dụng:
     - Phát hiện hành vi
     - Cảnh báo người dùng
     - Hạn chế tính năng