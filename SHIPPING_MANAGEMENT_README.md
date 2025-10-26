# Hướng dẫn sử dụng chức năng Quản lý Vận chuyển

## Tổng quan
Chức năng quản lý vận chuyển cho phép:
- **ADMIN**: Quản lý các phương thức vận chuyển (thêm, sửa, xóa, bật/tắt)
- **NGƯỜI DÙNG**: Chọn phương thức vận chuyển khi đặt hàng, phí vận chuyển được tự động tính vào tổng tiền

## Các file đã tạo/cập nhật

### 1. Entity & Repository
- ✅ `PhuongThucVanChuyen.java` - Entity cho phương thức vận chuyển
- ✅ `PhuongThucVanChuyenRepository.java` - Repository
- ✅ `DatHang.java` - Đã thêm trường `phuongThucVanChuyen` và `phiVanChuyen`

### 2. Service Layer
- ✅ `PhuongThucVanChuyenService.java` - Interface
- ✅ `PhuongThucVanChuyenServiceImpl.java` - Implementation
- ✅ `DatHangServiceImpl.java` - Đã cập nhật để xử lý phí vận chuyển

### 3. Controller
- ✅ `AdminPhuongThucVanChuyenController.java` - Controller cho Admin
- ✅ `OrderController.java` - Đã cập nhật để truyền danh sách phương thức vận chuyển

### 4. Model
- ✅ `DatHangRequest.java` - Đã thêm trường `maPhuongThuc`

### 5. Templates (HTML)
- ✅ `admin/shipping-methods/list.html` - Trang danh sách phương thức vận chuyển
- ✅ `admin/shipping-methods/form.html` - Form thêm/sửa phương thức vận chuyển
- ✅ `web/order.html` - Đã thêm phần chọn phương thức vận chuyển
- ✅ `admin/fragments/navbar.html` - Đã thêm menu "Quản lý Vận chuyển"

### 6. CSS & JavaScript
- ✅ `order.css` - Đã thêm CSS cho phần phương thức vận chuyển
- ✅ `order.html` - Đã thêm JavaScript để tính phí vận chuyển tự động

### 7. SQL Script
- ✅ `data-shipping-methods.sql` - Script khởi tạo dữ liệu mẫu

## Cách sử dụng

### Bước 1: Chạy Script SQL
```sql
-- Chạy file: src/main/resources/data-shipping-methods.sql
-- Script này sẽ tạo 4 phương thức vận chuyển mẫu
```

### Bước 2: Truy cập trang Admin
1. Đăng nhập với tài khoản ADMIN
2. Vào menu "Quản lý Vận chuyển" ở sidebar
3. URL: `http://localhost:8080/admin/shipping-methods`

### Bước 3: Quản lý phương thức vận chuyển
- **Thêm mới**: Click "Thêm phương thức mới"
- **Chỉnh sửa**: Click icon Edit (bút chì)
- **Đổi trạng thái**: Click icon Toggle (công tắc)
- **Xóa**: Click icon Delete (thùng rác)

### Bước 4: Người dùng đặt hàng
1. Người dùng thêm sản phẩm vào giỏ hàng
2. Vào trang đặt hàng: `http://localhost:8080/orders`
3. Chọn địa chỉ giao hàng
4. **Chọn phương thức vận chuyển** (bắt buộc)
5. Phí vận chuyển sẽ tự động được cộng vào tổng tiền
6. Chọn phương thức thanh toán và hoàn tất đơn hàng

## Cấu trúc Database

### Bảng PhuongThucVanChuyen
```sql
CREATE TABLE PhuongThucVanChuyen (
    MaPhuongThuc INT PRIMARY KEY IDENTITY(1,1),
    TenNhaVanChuyen NVARCHAR(200) NOT NULL,
    MoTa NVARCHAR(500),
    PhiVanChuyen DECIMAL(18,2) NOT NULL,
    ThoiGianGiaoHangDuKien NVARCHAR(100),
    TrangThai BIT DEFAULT 1,
    ThuTu INT DEFAULT 0
);
```

### Cập nhật bảng DatHang
```sql
ALTER TABLE DatHang ADD MaPhuongThuc INT;
ALTER TABLE DatHang ADD PhiVanChuyen DECIMAL(18,2) DEFAULT 0;
ALTER TABLE DatHang ADD CONSTRAINT FK_DatHang_PhuongThuc 
    FOREIGN KEY (MaPhuongThuc) REFERENCES PhuongThucVanChuyen(MaPhuongThuc);
```

## Tính năng

### Trang Admin - Quản lý Phương thức vận chuyển
✅ Hiển thị danh sách phương thức vận chuyển
✅ Thêm phương thức vận chuyển mới
✅ Chỉnh sửa thông tin phương thức vận chuyển
✅ Bật/Tắt trạng thái (chỉ phương thức đang hoạt động mới hiển thị cho người dùng)
✅ Xóa phương thức vận chuyển
✅ Sắp xếp theo thứ tự hiển thị

### Trang Đặt hàng - Người dùng
✅ Hiển thị danh sách phương thức vận chuyển đang hoạt động
✅ Hiển thị tên, mô tả, thời gian giao hàng dự kiến, phí vận chuyển
✅ Cho phép chọn 1 phương thức vận chuyển (bắt buộc)
✅ Tự động tính phí vận chuyển vào tổng tiền
✅ Cập nhật tổng tiền real-time khi thay đổi phương thức vận chuyển
✅ Lưu thông tin phương thức vận chuyển và phí vào đơn hàng

## Cách tính tổng tiền

Tổng tiền đơn hàng = (Tổng tiền sản phẩm - Giảm giá khuyến mãi) + Phí vận chuyển

Ví dụ:
- Tổng tiền sản phẩm: 500,000đ
- Giảm giá: 50,000đ (10%)
- Phí vận chuyển: 30,000đ
- **Tổng cộng: 480,000đ**

## Lưu ý
- Phương thức vận chuyển phải được chọn trước khi đặt hàng
- Chỉ các phương thức có trạng thái = true mới hiển thị cho người dùng
- Phí vận chuyển được lưu vào đơn hàng tại thời điểm đặt hàng (không thay đổi nếu sau này admin sửa phí)
- Có thể tạo phương thức "Giao hàng miễn phí" với phí = 0đ

## Khắc phục sự cố

### Lỗi: Không hiển thị phương thức vận chuyển
➡️ Kiểm tra xem đã chạy script SQL chưa
➡️ Kiểm tra trạng thái của phương thức (phải = true)

### Lỗi: Phí vận chuyển không được tính
➡️ Kiểm tra JavaScript console để xem có lỗi không
➡️ Đảm bảo đã chọn phương thức vận chuyển trước khi đặt hàng

### Lỗi: Không thể truy cập trang admin
➡️ Đảm bảo đăng nhập với tài khoản có role ADMIN
