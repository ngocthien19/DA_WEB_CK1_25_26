# Đặc tả chức năng theo Role

## Tổng quan
Dự án là một hệ thống thương mại điện tử hoàn chỉnh với các vai trò: Admin, Vendor (Người bán), User (Người dùng), và Shipper (Người giao hàng). Mỗi vai trò có các chức năng riêng biệt, được thiết kế để tối ưu hóa trải nghiệm người dùng và hiệu quả quản lý.

## 1. Chức năng chung (Authentication)

### 1.1. Đăng nhập
- **Mô tả**: Cho phép người dùng đăng nhập vào hệ thống
- **Tác nhân**: Tất cả (Admin, Vendor, User, Shipper)
- **Điều kiện trước**: Người dùng đã có tài khoản trong hệ thống
- **Điều kiện tiên quyết**: Không
- **Luồng chính**:
  1. Người dùng nhập email và mật khẩu
  2. Hệ thống xác thực thông tin
  3. Hệ thống tạo JWT token
  4. Hệ thống trả về thông tin người dùng và token
- **Luồng thay thế**: Không có
- **Luồng ngoại lệ**:
  - Email hoặc mật khẩu không đúng: Hệ thống thông báo lỗi
  - Tài khoản chưa xác thực: Yêu cầu xác thực email

### 1.2. Đăng ký
- **Mô tả**: Cho phép người dùng tạo tài khoản mới
- **Tác nhân**: User
- **Điều kiện trước**: Email chưa được sử dụng trong hệ thống
- **Điều kiện tiên quyết**: Không
- **Luồng chính**:
  1. Người dùng nhập thông tin đăng ký
  2. Hệ thống gửi mã OTP qua email
  3. Người dùng xác thực OTP
  4. Tài khoản được tạo và kích hoạt
- **Luồng ngoại lệ**:
  - Email đã tồn tại: Thông báo lỗi
  - OTP không hợp lệ: Yêu cầu nhập lại
  - OTP hết hạn: Yêu cầu gửi lại

### 1.3. Quên mật khẩu
- **Mô tả**: Cho phép người dùng khôi phục mật khẩu
- **Tác nhân**: Tất cả
- **Điều kiện trước**: Tài khoản tồn tại trong hệ thống
- **Điều kiện tiên quyết**: Không
- **Luồng chính**:
  1. Người dùng nhập email
  2. Hệ thống gửi mã OTP
  3. Người dùng xác thực OTP
  4. Người dùng đặt mật khẩu mới
- **Luồng ngoại lệ**:
  - Email không tồn tại
  - OTP không hợp lệ/hết hạn

## 2. Chức năng Admin

### 2.1. Quản lý Cấu hình Hệ thống
- **Mô tả**: Quản lý các cấu hình và thiết lập hệ thống
- **Tác nhân**: Admin
- **Điều kiện trước**: Đăng nhập với quyền Admin
- **Luồng chính**:
  1. Quản lý phương thức vận chuyển:
     - Cấu hình dịch vụ:
       + Thêm nhà vận chuyển
       + Cài đặt phí vận chuyển
       + Thiết lập vùng phục vụ
     - Quản lý chính sách:
       + Thời gian giao dự kiến
       + Chính sách hoàn trả
       + Bảo hiểm hàng hóa
     - Theo dõi hiệu quả:
       + Tỷ lệ giao thành công
       + Chi phí vận chuyển
       + Đánh giá dịch vụ

  2. Quản lý thanh toán:
     - Cấu hình cổng thanh toán:
       + Kết nối VietQR/Momo/VNPay
       + Thiết lập phí giao dịch
       + Cài đặt mã merchant
     - Quản lý giao dịch:
       + Theo dõi trạng thái
       + Xử lý hoàn tiền
       + Đối soát thanh toán
     - Bảo mật:
       + Mã hóa thông tin
       + Giới hạn giao dịch
       + Phát hiện gian lận

  3. Thiết lập hệ thống:
     - Cấu hình chung:
       + Logo/thương hiệu
       + Email hệ thống
       + Thông báo chung
     - Quản lý cache:
       + Xóa cache
       + Tối ưu hiệu suất
       + Đồng bộ dữ liệu
     - Backup:
       + Lịch backup
       + Khôi phục dữ liệu
       + Lưu trữ an toàn

### 2.2. Quản lý Khách hàng
- **Mô tả**: Quản lý thông tin và hoạt động khách hàng
- **Tác nhân**: Admin
- **Điều kiện trước**: Đăng nhập Admin
- **Luồng chính**:
  1. Quản lý tài khoản:
     - Thông tin khách hàng:
       + Xem chi tiết cá nhân
       + Lịch sử mua hàng
       + Điểm thưởng/hạng
     - Phân loại khách:
       + Theo giá trị
       + Theo tần suất mua
       + Theo khu vực
     - Tương tác:
       + Gửi thông báo
       + Email marketing
       + Khảo sát ý kiến

  2. Xử lý khiếu nại:
     - Tiếp nhận:
       + Phân loại vấn đề
       + Mức độ ưu tiên
       + Phân công xử lý
     - Theo dõi:
       + Tiến độ giải quyết
       + Thời gian phản hồi
       + Đánh giá hài lòng
     - Báo cáo:
       + Thống kê khiếu nại
       + Phân tích nguyên nhân
       + Đề xuất cải thiện

  3. Chương trình khách hàng:
     - Tích điểm:
       + Cài đặt tỷ lệ
       + Quản lý điểm
       + Quy đổi ưu đãi
     - Hạng thành viên:
       + Thiết lập tiêu chí
       + Quyền lợi đặc biệt
       + Thăng/giảm hạng
     - Quà tặng:
       + Sinh nhật/dịp lễ
       + Khách VIP
       + Chiến dịch đặc biệt

### 2.3. Quản lý Bảo mật và Phân quyền
- **Mô tả**: Quản lý bảo mật và phân quyền hệ thống
- **Tác nhân**: Admin
- **Điều kiện trước**: Đăng nhập với quyền Admin cao cấp
- **Luồng chính**:
  1. Quản lý vai trò:
     - Thiết lập vai trò:
       + Tạo vai trò mới
       + Định nghĩa quyền hạn
       + Phân cấp quản lý
     - Phân quyền chi tiết:
       + Theo chức năng
       + Theo dữ liệu
       + Theo thời gian
     - Kiểm soát truy cập:
       + Giới hạn IP
       + Thời gian truy cập
       + Đa thiết bị

  2. Giám sát bảo mật:
     - Theo dõi hoạt động:
       + Log đăng nhập
       + Thao tác quan trọng
       + Truy cập bất thường
     - Cảnh báo bảo mật:
       + Phát hiện tấn công
       + Login bất thường
       + Thay đổi quan trọng
     - Xử lý sự cố:
       + Khóa tài khoản
       + Cô lập đe dọa
       + Khôi phục hệ thống

  3. Chính sách bảo mật:
     - Mật khẩu:
       + Độ phức tạp
       + Thời hạn thay đổi
       + Lịch sử mật khẩu
     - Xác thực:
       + 2FA/MFA
       + Captcha
       + Khóa sinh trắc học
     - Mã hóa dữ liệu:
       + Thông tin thanh toán
       + Dữ liệu cá nhân
       + Tin nhắn riêng tư

### 2.4. Thống kê và Báo cáo
- **Mô tả**: Dashboard thống kê tổng quan hệ thống
- **Tác nhân**: Admin
- **Điều kiện trước**: Đăng nhập với quyền Admin
- **Luồng chính**:
  1. Thống kê tổng quan:
     - Doanh số:
       + Doanh thu theo thời gian
       + So sánh các giai đoạn
       + Biểu đồ tăng trưởng
     - Đơn hàng:
       + Tổng số đơn hàng
       + Tỷ lệ hoàn thành
       + Đơn hủy/hoàn trả
     - Người dùng:
       + Số lượng theo role
       + Tài khoản mới
       + Tài khoản active

  2. Báo cáo chi tiết:
     - Phân tích bán hàng:
       + Top sản phẩm bán chạy
       + Top cửa hàng doanh thu
       + Hiệu quả khuyến mãi
     - Hoạt động vận chuyển:
       + Hiệu suất giao hàng
       + Thời gian trung bình
       + Tỷ lệ khiếu nại
     - Hoạt động người dùng:
       + Lượt truy cập
       + Thời gian sử dụng
       + Tương tác hệ thống

  3. Cài đặt báo cáo:
     - Tùy chỉnh hiển thị:
       + Chọn chỉ số theo dõi
       + Định dạng báo cáo
       + Giai đoạn thống kê
     - Xuất báo cáo:
       + PDF/Excel
       + Email định kỳ
       + Lưu trữ lịch sử

- **Luồng thay thế**:
  1. Báo cáo tự động:
     - Lập lịch gửi
     - Định dạng mẫu
  2. Phân tích chi tiết:
     - Drill-down dữ liệu
     - So sánh chỉ số

- **Luồng ngoại lệ**:
  1. Dữ liệu thiếu:
     - Đánh dấu không đủ
     - Ước tính tỷ lệ
  2. Lỗi tính toán:
     - Kiểm tra sai lệch
     - Đồng bộ lại số liệu

### 2.2. Quản lý người dùng
- **Mô tả**: Quản lý thông tin và phân quyền người dùng
- **Tác nhân**: Admin
- **Điều kiện trước**: Đăng nhập với quyền Admin
- **Luồng chính**:
  1. Xem danh sách người dùng
  2. Thêm/sửa/xóa người dùng
  3. Phân quyền người dùng
  4. Khóa/mở khóa tài khoản
- **Luồng ngoại lệ**: 
  - Không thể xóa tài khoản đang hoạt động
  - Không thể thay đổi quyền Admin cao cấp

### 2.2. Quản lý danh mục
- **Mô tả**: Quản lý các danh mục sản phẩm
- **Tác nhân**: Admin
- **Điều kiện trước**: Đăng nhập với quyền Admin
- **Luồng chính**:
  1. Xem danh sách danh mục
  2. Thêm/sửa/xóa danh mục
  3. Sắp xếp thứ tự danh mục
- **Luồng ngoại lệ**:
  - Không thể xóa danh mục có sản phẩm

## 3. Chức năng Vendor

### 3.1. Quản lý cửa hàng
- **Mô tả**: Quản lý thông tin và hoạt động cửa hàng
- **Tác nhân**: Vendor
- **Điều kiện trước**: Đăng nhập với quyền Vendor
- **Luồng chính**:
  1. Cập nhật thông tin cửa hàng
  2. Quản lý sản phẩm
  3. Xem đánh giá cửa hàng
  4. Quản lý đơn hàng
- **Luồng ngoại lệ**:
  - Cửa hàng bị khóa
  - Thông tin không hợp lệ

### 3.2. Quản lý Vận hành Cửa hàng
- **Mô tả**: Quản lý hoạt động và vận hành cửa hàng
- **Tác nhân**: Vendor
- **Điều kiện trước**: Có cửa hàng đang hoạt động
- **Luồng chính**:
  1. Quản lý đơn hàng:
     - Xử lý đơn mới:
       + Kiểm tra tồn kho
       + Xác nhận đơn hàng
       + In phiếu gói hàng
     - Xử lý vận chuyển:
       + Chọn đơn vị vận chuyển
       + In vận đơn
       + Bàn giao shipper
     - Theo dõi giao hàng:
       + Cập nhật trạng thái
       + Xử lý sự cố
       + Xác nhận hoàn thành

  2. Quản lý doanh thu:
     - Thu chi:
       + Doanh thu bán hàng
       + Chi phí vận chuyển
       + Phí hoa hồng
     - Đối soát:
       + Với hệ thống
       + Với vận chuyển
       + Với khách hàng
     - Rút tiền:
       + Yêu cầu rút tiền
       + Lịch sử giao dịch
       + Số dư khả dụng

  3. Quản lý nhân viên:
     - Phân quyền:
       + Tạo tài khoản
       + Cấp quyền truy cập
       + Theo dõi hoạt động
     - Hiệu suất:
       + Thời gian xử lý
       + Đơn hoàn thành
       + Đánh giá KPI
     - Lương thưởng:
       + Tính hoa hồng
       + Thưởng doanh số
       + Phạt vi phạm

### 3.3. Quản lý Kho và Vận chuyển
- **Mô tả**: Quản lý kho hàng và vận chuyển
- **Tác nhân**: Vendor
- **Điều kiện trước**: Có cửa hàng hoạt động
- **Luồng chính**:
  1. Quản lý kho hàng:
     - Nhập kho:
       + Tạo phiếu nhập
       + Kiểm tra chất lượng
       + Cập nhật tồn kho
     - Xuất kho:
       + Phiếu xuất tự động
       + Đối chiếu đơn hàng
       + Ghi nhận hao hụt
     - Kiểm kê:
       + Định kỳ kiểm kê
       + Đối chiếu thực tế
       + Báo cáo chênh lệch

  2. Quản lý vận chuyển:
     - Thiết lập giao hàng:
       + Khu vực phục vụ
       + Phí vận chuyển
       + Thời gian giao
     - Đối tác vận chuyển:
       + Chọn đơn vị
       + Đàm phán giá
       + Đánh giá chất lượng
     - Xử lý đặc biệt:
       + Hàng dễ vỡ
       + Hàng giá trị cao
       + Giao hỏa tốc

  3. Tối ưu vận hành:
     - Dự báo nhu cầu:
       + Theo mùa/sự kiện
       + Theo lịch sử bán
       + Theo xu hướng
     - Quản lý không gian:
       + Sắp xếp kho
       + Phân loại hàng
       + Tối ưu diện tích
     - Cảnh báo tự động:
       + Hàng sắp hết
       + Hàng tồn lâu
       + Hàng cận date

### 3.4. Quản lý Marketing và Bán hàng
- **Mô tả**: Quản lý hoạt động marketing và bán hàng
- **Tác nhân**: Vendor
- **Điều kiện trước**: Có cửa hàng hoạt động
- **Luồng chính**:
  1. Chiến dịch khuyến mãi:
     - Tạo khuyến mãi:
       + Loại khuyến mãi
       + Điều kiện áp dụng
       + Thời gian hiệu lực
     - Quản lý mã giảm giá:
       + Tạo mã tự động
       + Theo dõi sử dụng
       + Giới hạn sử dụng
     - Đánh giá hiệu quả:
       + Doanh số tăng thêm
       + Chi phí khuyến mãi
       + ROI chiến dịch

  2. Quảng cáo sản phẩm:
     - SEO sản phẩm:
       + Tối ưu tiêu đề
       + Mô tả sản phẩm
       + Tag/từ khóa
     - Quảng bá:
       + Flash sale
       + Sản phẩm nổi bật
       + Deal sốc
     - Media:
       + Hình ảnh chất lượng
       + Video sản phẩm
       + Review khách hàng

  3. Chăm sóc khách hàng:
     - Tương tác:
       + Chat với khách
       + Email marketing
       + Thông báo ưu đãi
     - Xử lý phản hồi:
       + Đánh giá sản phẩm
       + Khiếu nại/trả hàng
       + Hậu mãi
     - Khách hàng thân thiết:
       + Chính sách ưu đãi
       + Quà tặng sinh nhật
       + Dịch vụ VIP

### 3.4. Thống kê và Báo cáo Cửa hàng
- **Mô tả**: Dashboard thống kê hoạt động cửa hàng
- **Tác nhân**: Vendor
- **Điều kiện trước**: Có cửa hàng đang hoạt động
- **Luồng chính**:
  1. Thống kê doanh thu:
     - Doanh số bán hàng:
       + Theo ngày/tuần/tháng
       + Theo danh mục
       + Theo sản phẩm
     - Phân tích lợi nhuận:
       + Biên lợi nhuận
       + Chi phí vận chuyển
       + Chiết khấu khuyến mãi
     - So sánh kỳ vọng:
       + Mục tiêu đề ra
       + Tăng trưởng
       + Dự báo doanh số

  2. Phân tích đơn hàng:
     - Thống kê số lượng:
       + Đơn mới
       + Đang xử lý
       + Đã giao
       + Đã hủy
     - Phân tích khách hàng:
       + Khách hàng mới/cũ
       + Tần suất mua
       + Giá trị trung bình
     - Đánh giá chất lượng:
       + Rating trung bình
       + Phản hồi khách hàng
       + Tỷ lệ khiếu nại

  3. Quản lý hàng hóa:
     - Theo dõi tồn kho:
       + Số lượng hiện có
       + Mức tồn kho tối thiểu
       + Cảnh báo hết hàng
     - Phân tích bán hàng:
       + Sản phẩm bán chạy
       + Sản phẩm tồn đọng
       + Xu hướng thị trường
     - Hiệu quả khuyến mãi:
       + Doanh số khuyến mãi
       + Tỷ lệ chuyển đổi
       + ROI chiến dịch

- **Luồng thay thế**:
  1. Xuất báo cáo:
     - PDF chi tiết
     - File Excel
     - Biểu đồ thống kê
  2. Phân tích nâng cao:
     - So sánh đối thủ
     - Dự báo xu hướng
     - Gợi ý tối ưu

- **Luồng ngoại lệ**:
  1. Dữ liệu không đồng bộ:
     - Kiểm tra sai lệch
     - Đồng bộ thủ công
  2. Lỗi tính toán:
     - Báo cáo admin
     - Điều chỉnh số liệu
  3. Thống kê không đủ:
     - Đánh dấu thiếu
     - Ước tính tạm thời

### 3.3. Quản lý sản phẩm và khuyến mãi
- **Mô tả**: Quản lý sản phẩm và chương trình khuyến mãi
- **Tác nhân**: Vendor
- **Điều kiện trước**: Có cửa hàng đang hoạt động
- **Luồng chính**:
  1. Quản lý sản phẩm:
     - Thêm sản phẩm mới:
       + Thông tin cơ bản
       + Mô tả chi tiết
       + Thông số kỹ thuật
       + SEO meta tags
     - Upload hình ảnh:
       + Nhiều ảnh sản phẩm
       + Sắp xếp thứ tự
       + Tối ưu kích thước
     - Quản lý tồn kho:
       + Cập nhật số lượng
       + Cảnh báo hết hàng
       + Lịch sử xuất/nhập

  2. Quản lý khuyến mãi:
     - Tạo khuyến mãi mới:
       + Mã giảm giá (3-50 ký tự)
       + Mức giảm (0-100%)
       + Thời gian áp dụng
       + Số lượng mã giới hạn
     - Thiết lập điều kiện:
       + Giá trị đơn tối thiểu
       + Sản phẩm áp dụng
       + Đối tượng khách hàng
     - Theo dõi hiệu quả:
       + Số lượng đã dùng
       + Doanh số giảm giá
       + Tỷ lệ chuyển đổi

  3. Quản lý giá:
     - Cập nhật giá bán:
       + Giá niêm yết
       + Giá khuyến mãi
       + Giá theo số lượng
     - Lịch sử thay đổi giá
     - So sánh với thị trường

- **Luồng thay thế**:
  1. Nhập/Xuất hàng loạt:
     - Import từ Excel
     - Cập nhật nhiều sản phẩm
  2. Sao chép khuyến mãi:
     - Dùng lại mẫu cũ
     - Điều chỉnh thông số

- **Luồng ngoại lệ**:
  1. Sản phẩm:
     - Trùng mã/tên
     - Ảnh không hợp lệ
     - Thông tin thiếu
  2. Khuyến mãi:
     - Mã trùng lặp
     - Thời gian không hợp lệ
     - Vượt giới hạn giảm
  3. Giá bán:
     - Thấp hơn giá vốn
     - Vượt ngưỡng cho phép

## 4. Chức năng User

### 4.1. Quản lý giỏ hàng
- **Mô tả**: Thao tác với giỏ hàng
- **Tác nhân**: User
- **Điều kiện trước**: Đăng nhập
- **Luồng chính**:
  1. Thêm sản phẩm vào giỏ
  2. Cập nhật số lượng
  3. Xóa sản phẩm
  4. Thanh toán
- **Luồng ngoại lệ**:
  - Sản phẩm hết hàng
  - Số lượng vượt tồn kho

### 4.2. Đặt hàng và Thanh toán
- **Mô tả**: Quy trình đặt hàng và thanh toán hoàn chỉnh
- **Tác nhân**: User
- **Điều kiện trước**: 
  - Có sản phẩm trong giỏ hàng
  - Đã đăng nhập vào hệ thống
- **Luồng chính**:
  1. Xác nhận thông tin đơn hàng:
     - Địa chỉ giao hàng:
       + Chọn từ danh sách có sẵn
       + Thêm địa chỉ mới
       + Đặt làm mặc định
     - Thông tin liên hệ:
       + Tên người nhận
       + Số điện thoại
       + Ghi chú đặc biệt
  
  2. Chọn phương thức vận chuyển:
     - So sánh các đơn vị:
       + Phí vận chuyển
       + Thời gian dự kiến
       + Đánh giá dịch vụ
     - Xem chi tiết dịch vụ
     - Chọn phương thức phù hợp

  3. Áp dụng khuyến mãi:
     - Nhập mã giảm giá
     - Kiểm tra điều kiện:
       + Giá trị đơn tối thiểu
       + Thời hạn sử dụng
       + Số lượng còn lại
     - Hiển thị số tiền giảm

  4. Thanh toán:
     a. Thanh toán khi nhận hàng (COD):
        - Xác nhận thông tin
        - Tạo phiếu thu COD
     b. Chuyển khoản qua VietQR:
        - Hiển thị mã QR
        - Thông tin tài khoản:
          + Tên ngân hàng: Agribank
          + Số tài khoản
          + Tên người thụ hưởng
          + Nội dung chuyển khoản
        - Theo dõi trạng thái
     c. Ví điện tử/Thẻ:
        - Chuyển đến cổng thanh toán
        - Xác thực giao dịch

  5. Hoàn tất đặt hàng:
     - Tạo mã đơn hàng
     - Tính tổng thanh toán:
       + Tiền hàng
       + Phí vận chuyển
       + Giảm giá
     - Gửi email xác nhận
     - Chuyển đến trang theo dõi

- **Luồng thay thế**:
  1. Thanh toán nhiều đơn:
     - Gộp đơn hàng
     - Thanh toán một lần
  2. Lưu giỏ hàng:
     - Để mua sau
     - Chờ mã giảm giá

- **Luồng ngoại lệ**:
  1. Thanh toán thất bại:
     - Ghi nhận lỗi
     - Cho phép thử lại
     - Đổi phương thức
  2. Sản phẩm hết hàng:
     - Thông báo người dùng
     - Đề xuất thay thế
  3. Mã giảm giá lỗi:
     - Hiển thị lý do
     - Đề xuất mã khác
  4. Địa chỉ không hỗ trợ:
     - Thông báo vùng
     - Yêu cầu đổi địa chỉ

### 4.3. Đánh giá và Phản hồi
- **Mô tả**: Hệ thống đánh giá sản phẩm và cửa hàng
- **Tác nhân**: User
- **Điều kiện trước**: Đã nhận hàng thành công
- **Luồng chính**:
  1. Đánh giá sản phẩm:
     - Chọn sản phẩm:
       + Từ đơn hàng
       + Từ lịch sử mua
     - Viết đánh giá:
       + Chọn số sao (1-5)
       + Viết nhận xét
       + Đăng ảnh/video
     - Cập nhật đánh giá:
       + Chỉnh sửa nội dung
       + Thêm/xóa media
       + Phản hồi vendor

  2. Đánh giá cửa hàng:
     - Tiêu chí đánh giá:
       + Chất lượng sản phẩm
       + Dịch vụ khách hàng
       + Tốc độ phản hồi
       + Đóng gói giao hàng
     - Thông tin chi tiết:
       + Rating 1-5 sao
       + Bình luận (10-1000 ký tự)
       + Gắn với đơn hàng cụ thể
     - Hiển thị đánh giá:
       + Tổng hợp rating
       + Lọc theo thời gian
       + Sắp xếp theo mức độ

  3. Quản lý phản hồi:
     - Tương tác:
       + Xem phản hồi shop
       + Trả lời bình luận
       + Cập nhật đánh giá
     - Báo cáo vi phạm:
       + Nội dung không phù hợp
       + Thông tin sai lệch
       + Spam/quấy rối

- **Luồng thay thế**:
  1. Đánh giá nhanh:
     - Chỉ chọn số sao
     - Dùng mẫu có sẵn
  2. Gửi riêng cho shop:
     - Feedback nội bộ
     - Không hiển thị công khai

- **Luồng ngoại lệ**:
  1. Đơn hàng không hợp lệ:
     - Chưa hoàn thành
     - Đã quá hạn đánh giá
     - Đã đánh giá trước đó
  2. Nội dung vi phạm:
     - Từ khóa cấm
     - Hình ảnh không phù hợp
     - Thông tin cá nhân
  3. Tranh chấp đánh giá:
     - Shop khiếu nại
     - Người dùng khiếu nại
     - Xác minh thông tin

## 5. Chức năng Shipper

### 5.1. Quản lý Vận chuyển và Giao hàng
- **Mô tả**: Quy trình xử lý vận chuyển đơn hàng
- **Tác nhân**: Shipper
- **Điều kiện trước**: 
  - Đăng nhập với quyền Shipper
  - Được phân công đơn hàng
- **Luồng chính**:
  1. Tiếp nhận và xử lý đơn hàng:
     - Xem danh sách đơn:
       + Thông tin chi tiết
       + Địa chỉ giao nhận
       + Yêu cầu đặc biệt
     - Lập kế hoạch giao:
       + Sắp xếp lộ trình
       + Ước tính thời gian
       + Tối ưu quãng đường

  2. Quy trình lấy hàng:
     - Tại cửa hàng:
       + Quét mã QR đơn hàng
       + Kiểm tra sản phẩm
       + Xác nhận với shop
     - Cập nhật trạng thái:
       + Đã lấy hàng
       + Bắt đầu giao
       + Ghi chú đặc biệt

  3. Quy trình giao hàng:
     - Liên hệ khách hàng:
       + Xác nhận địa chỉ
       + Thông báo thời gian
       + Hướng dẫn kiểm tra
     - Xác nhận giao hàng:
       + Thu tiền (nếu COD)
       + Chụp ảnh bằng chứng
       + Lấy chữ ký (nếu cần)
     - Hoàn tất giao hàng:
       + Cập nhật trạng thái
       + Đồng bộ hệ thống
       + Báo cáo hoàn thành

  4. Xử lý tình huống đặc biệt:
     - Không giao được:
       + Ghi nhận lý do
       + Chụp ảnh hiện trường
       + Liên hệ hỗ trợ
     - Khách từ chối nhận:
       + Lập biên bản
       + Xác nhận với khách
       + Báo cáo hệ thống
     - Hàng bị hư hỏng:
       + Đánh giá thiệt hại
       + Chụp ảnh bằng chứng
       + Xử lý theo quy trình

- **Luồng thay thế**:
  1. Giao hàng hẹn giờ:
     - Lên lịch cụ thể
     - Nhắc nhở tự động
  2. Giao lại lần 2:
     - Xác nhận thời gian
     - Cập nhật lộ trình

- **Luồng ngoại lệ**:
  1. Liên hệ thất bại:
     - Ghi nhận số lần gọi
     - Chờ theo quy định
     - Báo hỗ trợ xử lý
  2. Địa chỉ không chính xác:
     - Xác minh lại thông tin
     - Liên hệ người đặt
     - Điều chỉnh lộ trình
  3. Sự cố phương tiện:
     - Báo cáo ngay lập tức
     - Chuyển đơn nếu cần
     - Thông báo các bên

### 5.2. Theo dõi lộ trình
- **Mô tả**: Cập nhật vị trí và lộ trình giao hàng
- **Tác nhân**: Shipper
- **Điều kiện trước**: Có đơn hàng đang giao
- **Luồng chính**:
  1. Bắt đầu hành trình
  2. Cập nhật vị trí real-time
  3. Ghi nhận điểm dừng
  4. Hoàn thành hành trình
- **Luồng ngoại lệ**:
  - GPS không hoạt động
  - Lỗi kết nối

### 5.3. Chat với khách hàng
- **Mô tả**: Liên lạc với khách hàng
- **Tác nhân**: Shipper
- **Điều kiện trước**: Có đơn hàng đang giao
- **Luồng chính**:
  1. Chọn đơn hàng cần liên hệ
  2. Gửi tin nhắn
  3. Nhận phản hồi
  4. Kết thúc cuộc trò chuyện
- **Luồng ngoại lệ**:
  - Khách hàng không phản hồi
  - Lỗi kết nối

## 6. Quy trình Khiếu nại và Hoàn tiền

### 6.1. Xử lý Khiếu nại
- **Mô tả**: Quy trình xử lý khiếu nại và tranh chấp
- **Tác nhân**: User, Vendor, Admin
- **Điều kiện trước**: Có đơn hàng phát sinh vấn đề
- **Luồng chính**:
  1. Tạo khiếu nại:
     - Thông tin khiếu nại:
       + Chọn đơn hàng
       + Loại vấn đề
       + Mô tả chi tiết
     - Bằng chứng:
       + Upload hình ảnh
       + Video quay lại
       + Hóa đơn liên quan
     - Yêu cầu giải quyết:
       + Hoàn tiền
       + Đổi/trả hàng
       + Bồi thường

  2. Xử lý khiếu nại:
     - Tiếp nhận:
       + Phân loại mức độ
       + Chuyển bộ phận
       + Hẹn thời gian
     - Điều tra:
       + Thu thập thông tin
       + Xác minh bằng chứng
       + Liên hệ các bên
     - Phương án giải quyết:
       + Đề xuất giải pháp
       + Thương lượng
       + Ra quyết định

  3. Hoàn tất xử lý:
     - Thực hiện giải pháp:
       + Hoàn tiền nếu cần
       + Đổi/trả hàng
       + Bồi thường thiệt hại
     - Cập nhật hệ thống:
       + Trạng thái khiếu nại
       + Lịch sử xử lý
       + Đánh giá kết quả

### 6.2. Quy trình Hoàn tiền
- **Mô tả**: Xử lý yêu cầu và thực hiện hoàn tiền
- **Tác nhân**: Admin, Vendor
- **Điều kiện trước**: Có yêu cầu hoàn tiền hợp lệ
- **Luồng chính**:
  1. Xác nhận yêu cầu:
     - Kiểm tra điều kiện:
       + Thời gian yêu cầu
       + Lý do hoàn tiền
       + Trạng thái đơn hàng
     - Tính toán số tiền:
       + Giá trị hoàn
       + Phí phát sinh
       + Khấu trừ (nếu có)

  2. Thực hiện hoàn tiền:
     - Theo phương thức:
       + Hoàn vào ví điện tử
       + Chuyển khoản ngân hàng
       + Hoàn về thẻ gốc
     - Quy trình hoàn:
       + Tạo lệnh hoàn tiền
       + Xác nhận giao dịch
       + Thông báo các bên

  3. Theo dõi và xác nhận:
     - Kiểm tra giao dịch:
       + Trạng thái hoàn tiền
       + Thời gian xử lý
       + Biên bản xác nhận
     - Thông báo kết quả:
       + Email thông báo
       + Cập nhật trạng thái
       + Lưu hồ sơ

- **Luồng thay thế**:
  1. Hoàn tiền một phần:
     - Tính toán tỷ lệ
     - Xác nhận với khách
  2. Hoàn tiền theo đợt:
     - Chia thành nhiều lần
     - Lập lịch hoàn tiền

- **Luồng ngoại lệ**:
  1. Thông tin không hợp lệ:
     - Tài khoản sai
     - Thiếu thông tin
     - Yêu cầu bổ sung
  2. Lỗi giao dịch:
     - Hoàn tiền thất bại
     - Tài khoản bị khóa
     - Xử lý thủ công
  3. Tranh chấp hoàn tiền:
     - Khiếu nại số tiền
     - Thời gian xử lý
     - Chuyển bộ phận pháp lý

## 7. Bảo mật và Quản lý Phiên

### 7.1. Hệ thống Xác thực
- **Mô tả**: Quản lý xác thực và bảo mật người dùng
- **Tác nhân**: Tất cả
- **Điều kiện trước**: Không
- **Luồng chính**:
  1. Xác thực JWT:
     - Tạo token:
       + Sau khi đăng nhập
       + Chứa thông tin user
       + Thời gian hết hạn
     - Kiểm tra token:
       + Validate signature
       + Kiểm tra hết hạn
       + Xác thực quyền
     - Refresh token:
       + Tự động làm mới
       + Giữ phiên đăng nhập
       + Xử lý hết hạn

  2. Quản lý phiên:
     - Theo dõi hoạt động:
       + Thời gian đăng nhập
       + IP truy cập
       + Thiết bị sử dụng
     - Kiểm soát truy cập:
       + Phân quyền chi tiết
       + Giới hạn truy cập
       + Chặn IP đáng ngờ
     - Đăng xuất:
       + Thủ công
       + Tự động sau thời gian
       + Đồng bộ đa thiết bị

  3. Bảo mật nâng cao:
     - Mã hóa dữ liệu:
       + Thông tin người dùng
       + Dữ liệu thanh toán
       + Tin nhắn riêng tư
     - Chống tấn công:
       + CSRF protection
       + XSS prevention
       + SQL injection
     - Giám sát hệ thống:
       + Log hoạt động
       + Cảnh báo bất thường
       + Backup dữ liệu

- **Luồng thay thế**:
  1. Xác thực hai lớp:
     - SMS OTP
     - Email verification
     - Authenticator app
  2. Single Sign-On:
     - Đăng nhập Google
     - Đăng nhập Facebook
     - Đăng nhập Apple

- **Luồng ngoại lệ**:
  1. Token không hợp lệ:
     - Thông báo lỗi
     - Yêu cầu đăng nhập lại
     - Ghi log sự cố
  2. Truy cập bất thường:
     - Khóa tài khoản tạm thời
     - Thông báo email
     - Yêu cầu xác minh
  3. Tấn công bảo mật:
     - Phát hiện và chặn
     - Thông báo admin
     - Tăng cường bảo vệ

## 8. Tính năng Realtime

### 8.1. Chat Realtime
- **Mô tả**: Hệ thống chat realtime qua WebSocket
- **Tác nhân**: Tất cả người dùng
- **Điều kiện trước**: Đã đăng nhập
- **Luồng chính**:
  1. Thiết lập kết nối:
     - Khởi tạo WebSocket:
       + Xác thực người dùng
       + Tạo kết nối an toàn
       + Duy trì phiên
     - Quản lý trạng thái:
       + Online/Offline
       + Đang nhập tin nhắn
       + Đã xem tin nhắn

  2. Gửi/nhận tin nhắn:
     - Xử lý tin nhắn:
       + Gửi nội dung text
       + Upload file/hình ảnh
       + Gửi emoji/sticker
     - Thông báo realtime:
       + Tin nhắn mới
       + Đã đọc tin nhắn
       + Đang nhập tin nhắn
     - Lưu trữ và đồng bộ:
       + Lưu vào database
       + Đồng bộ đa thiết bị
       + Backup tin nhắn

  3. Quản lý chat room:
     - Tạo cuộc trò chuyện:
       + Chat 1-1 
       + Chat nhóm
       + Chat với cửa hàng
     - Tính năng nâng cao:
       + Tìm kiếm tin nhắn
       + Lọc theo thời gian
       + Xuất lịch sử chat

### 8.2. Theo dõi Đơn hàng Realtime
- **Mô tả**: Cập nhật trạng thái đơn hàng realtime
- **Tác nhân**: User, Vendor, Shipper
- **Điều kiện trước**: Có đơn hàng đang xử lý
- **Luồng chính**:
  1. Cập nhật trạng thái:
     - Vendor xử lý:
       + Nhận đơn mới
       + Đóng gói hàng
       + Bàn giao shipper
     - Shipper giao hàng:
       + Cập nhật vị trí
       + Thời gian dự kiến
       + Xác nhận giao hàng
     - User theo dõi:
       + Nhận thông báo
       + Xem vị trí realtime
       + Tương tác với shipper

  2. Thông báo tự động:
     - Cập nhật trạng thái:
       + Push notification
       + Email thông báo
       + SMS (với sự kiện quan trọng)
     - Cảnh báo:
       + Giao hàng trễ
       + Thay đổi địa chỉ
       + Yêu cầu hủy đơn

### 8.3. Thông báo Realtime
- **Mô tả**: Hệ thống thông báo realtime
- **Tác nhân**: Tất cả
- **Điều kiện trước**: Đã đăng nhập
- **Luồng chính**:
  1. Quản lý thông báo:
     - Phân loại:
       + Đơn hàng
       + Khuyến mãi
       + Hệ thống
       + Tương tác
     - Độ ưu tiên:
       + Khẩn cấp
       + Quan trọng
       + Thông thường

  2. Gửi thông báo:
     - Đa kênh:
       + WebSocket
       + Push notification
       + Email
       + SMS
     - Tùy chỉnh:
       + Theo sở thích
       + Theo thời gian
       + Theo loại thông báo

- **Luồng ngoại lệ**:
  1. Mất kết nối:
     - Tự động kết nối lại
     - Cache dữ liệu offline
     - Đồng bộ khi online
  2. Quá tải hệ thống:
     - Giới hạn tần suất
     - Load balancing
     - Thông báo bảo trì

## 9. Hệ thống Thanh toán Đa cổng

### 9.1. Tích hợp Cổng thanh toán
- **Mô tả**: Quản lý và xử lý thanh toán qua nhiều cổng
- **Tác nhân**: User, Admin
- **Điều kiện trước**: Có đơn hàng cần thanh toán
- **Luồng chính**:
  1. Thanh toán qua Momo:
     - Khởi tạo giao dịch:
       + Tạo mã đơn hàng
       + Tính tổng tiền
       + Tạo QR code
     - Xử lý thanh toán:
       + Chuyển đến Momo
       + Xác thực giao dịch
       + Nhận kết quả
     - Hoàn tất:
       + Cập nhật trạng thái
       + Gửi biên nhận
       + Lưu lịch sử

  2. Thanh toán qua VNPay:
     - Tạo giao dịch:
       + Sinh mã giao dịch
       + Tạo URL thanh toán
       + Chuyển hướng user
     - Xử lý callback:
       + Kiểm tra chữ ký
       + Xác nhận kết quả
       + Cập nhật đơn hàng
     - Thông báo:
       + Gửi email xác nhận
       + Cập nhật ví điện tử
       + Lưu trữ hóa đơn

  3. Thanh toán qua VietQR:
     - Tạo mã QR:
       + Thông tin ngân hàng
       + Số tiền thanh toán
       + Nội dung chuyển khoản
     - Theo dõi giao dịch:
       + Kiểm tra biến động
       + Đối chiếu thông tin
       + Xác nhận thanh toán
     - Hoàn tất:
       + Cập nhật trạng thái
       + Thông báo các bên
       + Xuất hóa đơn

### 9.2. Quản lý Giao dịch
- **Mô tả**: Theo dõi và quản lý các giao dịch thanh toán
- **Tác nhân**: Admin
- **Điều kiện trước**: Có giao dịch trong hệ thống
- **Luồng chính**:
  1. Theo dõi giao dịch:
     - Xem danh sách:
       + Giao dịch mới
       + Đang xử lý
       + Hoàn thành/Thất bại
     - Chi tiết giao dịch:
       + Thông tin thanh toán
       + Lịch sử trạng thái
       + Log giao dịch
     - Thống kê:
       + Theo cổng thanh toán
       + Theo thời gian
       + Theo trạng thái

  2. Xử lý ngoại lệ:
     - Giao dịch lỗi:
       + Phát hiện vấn đề
       + Phân tích nguyên nhân
       + Xử lý thủ công
     - Giao dịch treo:
       + Kiểm tra trạng thái
       + Liên hệ đối tác
       + Cập nhật thủ công
     - Hoàn tiền:
       + Tạo lệnh hoàn
       + Theo dõi tiến trình
       + Thông báo khách hàng

- **Luồng thay thế**:
  1. Thanh toán thủ công:
     - Xác nhận chuyển khoản
     - Cập nhật trạng thái
  2. Chia đợt thanh toán:
     - Tạo lịch thanh toán
     - Theo dõi từng đợt

- **Luồng ngoại lệ**:
  1. Lỗi cổng thanh toán:
     - Chuyển cổng dự phòng
     - Thông báo bảo trì
     - Log sự cố
  2. Gian lận thanh toán:
     - Phát hiện bất thường
     - Tạm khóa giao dịch
     - Điều tra xử lý
  3. Timeout giao dịch:
     - Hủy giao dịch
     - Thông báo user
     - Tạo giao dịch mới

## 10. Quản lý Sản phẩm Chuyên biệt

### 10.1. Quản lý Thú cưng
- **Mô tả**: Chức năng đặc biệt cho sản phẩm thú cưng
- **Tác nhân**: Vendor
- **Điều kiện trước**: Có cửa hàng thú cưng
- **Luồng chính**:
  1. Thêm thú cưng:
     - Thông tin cơ bản:
       + Loài/giống
       + Tuổi/giới tính
       + Nguồn gốc/xuất xứ
     - Thông tin sức khỏe:
       + Tiêm chủng
       + Giấy khám bệnh
       + Tình trạng sức khỏe
     - Đặc điểm:
       + Tính cách
       + Huấn luyện
       + Yêu cầu chăm sóc

  2. Quản lý thú cưng:
     - Theo dõi sức khỏe:
       + Lịch tiêm chủng
       + Khám định kỳ
       + Chế độ ăn
     - Quy trình bán:
       + Kiểm tra người mua
       + Tư vấn chăm sóc
       + Hỗ trợ sau bán
     - Dịch vụ đi kèm:
       + Khám thú y
       + Phụ kiện
       + Thức ăn

### 10.2. Quản lý Biến thể Sản phẩm
- **Mô tả**: Quản lý các phiên bản của sản phẩm
- **Tác nhân**: Vendor
- **Điều kiện trước**: Có sản phẩm cần tạo biến thể
- **Luồng chính**:
  1. Tạo biến thể:
     - Thuộc tính:
       + Màu sắc
       + Kích thước
       + Phiên bản
     - Giá và tồn kho:
       + Giá riêng
       + Số lượng
       + Mã SKU
     - Media:
       + Hình ảnh riêng
       + Video demo
       + Thông số kỹ thuật

  2. Quản lý bán hàng:
     - Theo dõi tồn kho:
       + Theo biến thể
       + Cảnh báo hết hàng
       + Đồng bộ số lượng
     - Báo cáo:
       + Thống kê bán hàng
       + So sánh biến thể
       + Xu hướng mua

### 10.3. Hệ thống Đánh giá Nâng cao
- **Mô tả**: Quản lý đánh giá chi tiết sản phẩm
- **Tác nhân**: User, Vendor
- **Điều kiện trước**: Có giao dịch hoàn thành
- **Luồng chính**:
  1. Đánh giá chi tiết:
     - Tiêu chí:
       + Chất lượng
       + Giá trị
       + Dịch vụ
     - Media:
       + Hình thực tế
       + Video review
       + So sánh mô tả
     - Phản hồi:
       + Like/Dislike
       + Bình luận phụ
       + Chia sẻ

  2. Phân tích đánh giá:
     - Thống kê:
       + Điểm trung bình
       + Phân bố sao
       + Từ khóa phổ biến
     - Xu hướng:
       + Theo thời gian
       + Theo nhóm khách
       + Theo khu vực

- **Luồng ngoại lệ**:
  1. Sản phẩm đặc biệt:
     - Yêu cầu giấy phép
     - Hạn chế địa lý
     - Quy định riêng
  2. Lỗi đồng bộ:
     - Số lượng không khớp
     - Giá không đồng nhất
     - Thông tin mâu thuẫn
  3. Vi phạm quy định:
     - Sản phẩm cấm
     - Thông tin sai lệch
     - Đánh giá giả

## 11. Tìm kiếm và Khám phá

### 11.1. Tìm kiếm Thông minh
- **Mô tả**: Hệ thống tìm kiếm với gợi ý và đề xuất
- **Tác nhân**: Tất cả người dùng
- **Điều kiện trước**: Không
- **Luồng chính**:
  1. Tìm kiếm nhanh:
     - Gợi ý tức thời:
       + Hiển thị khi gõ
       + Tối đa 10 kết quả
       + Kèm hình ảnh/giá
     - Đề xuất thông minh:
       + Sản phẩm liên quan
       + 6 đề xuất hàng đầu
       + Dựa trên độ phù hợp
     - Xử lý đa ngôn ngữ:
       + Hỗ trợ tiếng Việt
       + Bỏ dấu thông minh
       + URL encoding

  2. Tìm kiếm nâng cao:
     - Bộ lọc:
       + Theo danh mục
       + Khoảng giá
       + Đánh giá
       + Tình trạng hàng
     - Sắp xếp:
       + Giá tăng/giảm
       + Mới nhất
       + Bán chạy
       + Đánh giá cao
     - Kết quả:
       + Phân trang
       + Hiển thị grid/list
       + Thông tin chi tiết

  3. Lịch sử và Đề xuất:
     - Lịch sử tìm kiếm:
       + Lưu từ khóa
       + Tìm kiếm gần đây
       + Xóa lịch sử
     - Đề xuất cá nhân:
       + Dựa trên lịch sử
       + Sở thích người dùng
       + Xu hướng chung

### 11.2. Khám phá Sản phẩm
- **Mô tả**: Tính năng giúp người dùng khám phá sản phẩm
- **Tác nhân**: User
- **Điều kiện trước**: Không
- **Luồng chính**:
  1. Trang chủ thông minh:
     - Sections động:
       + Sản phẩm nổi bật
       + Deal hot
       + Xu hướng mua sắm
     - Cá nhân hóa:
       + Dựa trên lịch sử
       + Theo sở thích
       + Theo mùa/sự kiện

  2. Danh mục thông minh:
     - Phân loại:
       + Theo ngành hàng
       + Theo nhãn hiệu
       + Theo đặc tính
     - Điều hướng:
       + Menu đa cấp
       + Quick links
       + Tags phổ biến

  3. Gợi ý mua sắm:
     - Sản phẩm liên quan:
       + Cùng danh mục
       + Thường mua cùng
       + Có thể thích
     - Combo deals:
       + Gói sản phẩm
       + Mua kèm giảm giá
       + Set quà tặng

- **Luồng ngoại lệ**:
  1. Không có kết quả:
     - Gợi ý thay thế
     - Sửa lỗi chính tả
     - Mở rộng tìm kiếm
  2. Quá tải hệ thống:
     - Cache kết quả
     - Giới hạn requests
     - Tối ưu truy vấn
  3. Kết quả không phù hợp:
     - Báo cáo nội dung
     - Điều chỉnh thuật toán
     - Feedback người dùng

## 12. Báo cáo và Phân tích

### 12.1. Báo cáo Doanh thu
- **Mô tả**: Hệ thống báo cáo tổng hợp doanh thu
- **Tác nhân**: Admin, Vendor
- **Điều kiện trước**: Có dữ liệu giao dịch
- **Luồng chính**:
  1. Báo cáo tổng quan:
     - Doanh thu:
       + Theo ngày/tuần/tháng
       + So sánh các kỳ
       + Biểu đồ tăng trưởng
     - Chi tiết giao dịch:
       + Theo phương thức
       + Theo cửa hàng
       + Theo sản phẩm
     - Phân tích xu hướng:
       + Dự báo doanh thu
       + Mùa cao điểm
       + Hiệu quả khuyến mãi

  2. Báo cáo chi tiết:
     - Theo cửa hàng:
       + Top doanh thu
       + Tỷ lệ hoàn đơn
       + ROI marketing
     - Theo sản phẩm:
       + Best sellers
       + Tồn kho cao
       + Biên lợi nhuận
     - Theo khách hàng:
       + Phân khúc khách
       + Giá trị đơn TB
       + Tần suất mua

### 12.2. Phân tích Hoạt động
- **Mô tả**: Phân tích chi tiết hoạt động hệ thống
- **Tác nhân**: Admin
- **Điều kiện trước**: Có dữ liệu hoạt động
- **Luồng chính**:
  1. Phân tích người dùng:
     - Hành vi:
       + Lượt truy cập
       + Thời gian dùng
       + Tương tác
     - Chuyển đổi:
       + Tỷ lệ mua hàng
       + Giá trị giỏ hàng
       + Tỷ lệ rời bỏ
     - Phân khúc:
       + Theo độ tuổi
       + Theo khu vực
       + Theo sở thích

  2. Phân tích hiệu quả:
     - Marketing:
       + ROI chiến dịch
       + Hiệu quả khuyến mãi
       + Kênh thu hút
     - Vận hành:
       + Thời gian xử lý
       + Tỷ lệ hoàn thành
       + Chi phí vận hành
     - Dịch vụ:
       + Độ hài lòng
       + Thời gian hỗ trợ
       + Tỷ lệ giải quyết

### 12.3. Báo cáo Tự động
- **Mô tả**: Tự động hóa báo cáo định kỳ
- **Tác nhân**: Admin, Vendor
- **Điều kiện trước**: Thiết lập lịch báo cáo
- **Luồng chính**:
  1. Thiết lập báo cáo:
     - Cấu hình:
       + Loại báo cáo
       + Tần suất gửi
       + Định dạng
     - Người nhận:
       + Email nhận
       + Phân quyền xem
       + Tùy chỉnh nội dung

  2. Xử lý và gửi:
     - Tạo báo cáo:
       + Thu thập dữ liệu
       + Tính toán chỉ số
       + Tạo biểu đồ
     - Phân phối:
       + Gửi email
       + Lưu trữ PDF
       + Thông báo app

- **Luồng ngoại lệ**:
  1. Lỗi dữ liệu:
     - Thiếu thông tin
     - Dữ liệu sai
     - Không đồng bộ
  2. Lỗi gửi báo cáo:
     - Email bounce
     - File lỗi
     - Server quá tải
  3. Phân tích sai:
     - Số liệu bất thường
     - Công thức sai
     - Dữ liệu nhiễu

## 13. Kết luận và Phụ lục

### 13.1. Tổng quan Hệ thống
- Hệ thống thương mại điện tử với 4 vai trò chính:
  + Admin: Quản trị toàn bộ hệ thống
  + Vendor: Quản lý cửa hàng và sản phẩm
  + User: Mua sắm và tương tác
  + Shipper: Vận chuyển và giao hàng

- Các tính năng nổi bật:
  1. Hệ thống thanh toán đa cổng:
     - VietQR
     - Momo
     - VNPay
     - COD
  
  2. Chat và hỗ trợ realtime:
     - WebSocket
     - Đa kênh (chat, email, phone)
     - Tự động phản hồi
  
  3. Quản lý vận chuyển thông minh:
     - Tracking realtime
     - Tối ưu lộ trình
     - Xử lý ngoại lệ
  
  4. Analytics và báo cáo:
     - Dashboard tự động
     - Phân tích chi tiết
     - Dự báo xu hướng

### 13.2. Phụ lục Kỹ thuật
- **Yêu cầu Hệ thống**:
  1. Server:
     - Java Spring Boot
     - MySQL Database
     - Redis Cache
     - WebSocket Server
  
  2. Security:
     - JWT Authentication
     - Role-based Access Control
     - Data Encryption
  
  3. Integration:
     - Payment Gateways
     - Email Service
     - SMS Gateway
     - Map Services

- **API Endpoints**:
  1. Authentication:
     - /api/auth/login
     - /api/auth/register
     - /api/auth/verify-otp
  
  2. Products:
     - /api/products
     - /api/products/{id}
     - /api/products/search
  
  3. Orders:
     - /api/orders
     - /api/orders/{id}
     - /api/orders/tracking
  
  4. Chat:
     - /ws/chat
     - /api/messages
     - /api/notifications

### 13.3. Quy trình Triển khai
- **Môi trường**:
  1. Development:
     - Local environment
     - Testing database
     - Mock services
  
  2. Staging:
     - UAT environment
     - Test payment integration
     - Performance testing
  
  3. Production:
     - High availability setup
     - Load balancing
     - Monitoring system

- **Maintenance**:
  1. Backup:
     - Daily database backup
     - Transaction logs
     - User data
  
  2. Monitoring:
     - System health
     - Performance metrics
     - Error tracking
  
  3. Updates:
     - Security patches
     - Feature updates
     - Bug fixes

### 6.1. Hệ thống Chat và Hỗ trợ
- **Mô tả**: Hệ thống chat và hỗ trợ tích hợp
- **Tác nhân**: Tất cả (User, Vendor, Shipper, Admin)
- **Điều kiện trước**: Đăng nhập hệ thống
- **Luồng chính**:
  1. Chat giữa User và Vendor:
     - Khởi tạo chat:
       + Chọn cửa hàng
       + Xem thông tin shop
       + Bắt đầu trò chuyện
     - Trao đổi tin nhắn:
       + Soạn nội dung
       + Đính kèm file/ảnh
       + Gửi link sản phẩm
     - Quản lý hội thoại:
       + Xem lịch sử chat
       + Tìm kiếm tin nhắn
       + Lọc theo thời gian

  2. Chat với Shipper:
     - Trong quá trình giao:
       + Tự động kết nối
       + Chia sẻ vị trí
       + Cập nhật trạng thái
     - Tính năng đặc biệt:
       + Thông báo gần đến
       + Xác nhận thay đổi
       + Báo cáo sự cố

  3. Hệ thống hỗ trợ:
     - Phân loại vấn đề:
       + Đơn hàng
       + Thanh toán
       + Kỹ thuật
       + Khiếu nại
     - Xử lý yêu cầu:
       + Phân công xử lý
       + Theo dõi tiến độ
       + Đánh giá kết quả

  4. Quản lý tin nhắn:
     - Cài đặt thông báo:
       + Âm thanh
       + Push notification
       + Email
     - Bảo mật:
       + Mã hóa tin nhắn
       + Xác thực người dùng
       + Chống spam

- **Luồng thay thế**:
  1. Chat tự động:
     - Trả lời tự động
     - Gợi ý câu hỏi
     - Chuyển tới nhân viên
  2. Hỗ trợ qua email:
     - Tạo ticket
     - Theo dõi xử lý
     - Nhận thông báo

- **Luồng ngoại lệ**:
  1. Lỗi kết nối:
     - Lưu tin offline
     - Gửi lại tự động
     - Thông báo user
  2. File không hợp lệ:
     - Kiểm tra định dạng
     - Giới hạn dung lượng
     - Quét malware
  3. Chặn người dùng:
     - Vi phạm quy định
     - Spam/quấy rối
     - Thời gian chặn

### 6.2. Thông báo
- **Mô tả**: Hệ thống thông báo
- **Tác nhân**: Tất cả
- **Điều kiện trước**: Đăng nhập
- **Luồng chính**:
  1. Nhận thông báo
  2. Đọc thông báo
  3. Xóa thông báo
  4. Cài đặt thông báo
- **Luồng ngoại lệ**:
  - Lỗi kết nối
  - Thông báo đã hết hạn