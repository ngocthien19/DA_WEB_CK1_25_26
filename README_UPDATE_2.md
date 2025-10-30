# Cập nhật Đặc tả - Phần Đánh giá và Phản hồi

## 1. Hệ thống Đánh giá Sản phẩm

### 1.1. Đánh giá Sản phẩm
- **Mô tả**: Cho phép người dùng đánh giá sản phẩm đã mua
- **Tác nhân**: User
- **Điều kiện trước**: 
  - Đã mua và nhận sản phẩm
  - Đơn hàng ở trạng thái "Đã giao"
- **Luồng chính**:
  1. Tạo đánh giá mới:
     - Chọn số sao (1-5):
       + 1 sao: Rất không hài lòng
       + 2 sao: Không hài lòng
       + 3 sao: Bình thường
       + 4 sao: Hài lòng
       + 5 sao: Rất hài lòng
     - Viết bình luận:
       + Chia sẻ trải nghiệm
       + Ưu điểm sản phẩm
       + Nhược điểm sản phẩm
     - Upload media:
       + Hình ảnh sản phẩm
       + Video ngắn (nếu có)
  
  2. Quản lý đánh giá:
     - Xem lại đánh giá:
       + Lịch sử đánh giá
       + Chỉnh sửa nội dung
       + Cập nhật media
     - Phản hồi bình luận:
       + Trả lời vendor
       + Thảo luận với users khác
     - Xóa đánh giá
  
  3. Theo dõi tương tác:
     - Lượt thích/không thích
     - Phản hồi từ vendor
     - Thông báo cập nhật

### 1.2. Thống kê và Báo cáo
- **Mô tả**: Hệ thống phân tích đánh giá
- **Tác nhân**: Admin, Vendor
- **Điều kiện trước**: Có dữ liệu đánh giá
- **Luồng chính**:
  1. Phân tích đánh giá:
     - Thống kê tổng quan:
       + Điểm trung bình
       + Phân bố số sao
       + Số lượng đánh giá
     - Phân tích chi tiết:
       + Từ khóa phổ biến
       + Chủ đề phản hồi
       + Xu hướng đánh giá
     - Báo cáo định kỳ:
       + Theo ngày/tuần/tháng
       + So sánh thời kỳ
       + Dự báo xu hướng
  
  2. Quản lý nội dung:
     - Kiểm duyệt đánh giá:
       + Phát hiện spam
       + Lọc nội dung không phù hợp
       + Ẩn/hiện đánh giá
     - Phản hồi tự động:
       + Mẫu câu trả lời
       + Thông báo xử lý
       + Cập nhật trạng thái

- **Luồng ngoại lệ**:
  1. Nội dung không phù hợp:
     - Phát hiện vi phạm
     - Gửi cảnh báo user
     - Ẩn tạm thời
  
  2. Tranh chấp đánh giá:
     - Vendor khiếu nại
     - Xác minh thông tin
     - Phân xử khách quan
  
  3. Lỗi kỹ thuật:
     - Upload file thất bại
     - Định dạng không hỗ trợ
     - Lưu trữ bị lỗi

### 1.3. Tương tác và Gamification
- **Mô tả**: Tính năng tương tác và khuyến khích đánh giá
- **Tác nhân**: User
- **Điều kiện trước**: Đã đăng nhập
- **Luồng chính**:
  1. Hệ thống điểm thưởng:
     - Tích điểm:
       + Đánh giá có hình ảnh
       + Bình luận chi tiết
       + Đánh giá được vote hữu ích
     - Đổi quà:
       + Voucher giảm giá
       + Ưu đãi đặc biệt
       + Huy hiệu người dùng
  
  2. Tương tác cộng đồng:
     - Vote đánh giá:
       + Đánh dấu hữu ích
       + Chia sẻ đánh giá
       + Bình luận phụ
     - Xếp hạng người dùng:
       + Người đánh giá tích cực
       + Đánh giá chất lượng
       + Ảnh hưởng cộng đồng

  3. Chương trình khuyến khích:
     - Nhắc nhở đánh giá:
       + Sau khi nhận hàng
       + Gợi ý nội dung
       + Hướng dẫn chi tiết
     - Phần thưởng đặc biệt:
       + Đánh giá đầu tiên
       + Đánh giá của tháng
       + Người dùng tiêu biểu

- **Luồng ngoại lệ**:
  1. Lạm dụng hệ thống:
     - Phát hiện spam
     - Khóa tính năng
     - Trừ điểm thưởng
  
  2. Tranh chấp điểm:
     - Kiểm tra lịch sử
     - Xác minh hoạt động
     - Điều chỉnh thủ công
  
  3. Lỗi tích điểm:
     - Ghi nhận sự cố
     - Hoàn điểm thủ công
     - Bồi thường nếu cần