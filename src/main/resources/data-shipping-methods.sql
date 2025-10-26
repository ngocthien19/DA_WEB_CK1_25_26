-- Script khởi tạo dữ liệu mẫu cho Phương thức vận chuyển
-- Chạy script này sau khi đã tạo bảng PhuongThucVanChuyen

-- Xóa dữ liệu cũ (nếu có)
DELETE FROM PhuongThucVanChuyen;

-- Thêm các phương thức vận chuyển mẫu
INSERT INTO PhuongThucVanChuyen (TenNhaVanChuyen, MoTa, PhiVanChuyen, ThoiGianGiaoHangDuKien, TrangThai, ThuTu)
VALUES 
    (N'Giao hàng tiêu chuẩn', N'Dịch vụ giao hàng tiêu chuẩn, phù hợp cho các đơn hàng không gấp', 30000, N'3-5 ngày làm việc', 1, 1),
    (N'Giao hàng nhanh', N'Giao hàng nhanh trong nội thành, nhận hàng trong 1-2 ngày', 50000, N'1-2 ngày làm việc', 1, 2),
    (N'Giao hàng hỏa tốc', N'Giao hàng trong ngày với đơn hàng đặt trước 12h trưa', 80000, N'Giao trong 4-6 giờ', 1, 3),
    (N'Giao hàng miễn phí', N'Miễn phí giao hàng cho đơn từ 500,000đ', 0, N'3-7 ngày làm việc', 1, 4);

-- Kiểm tra dữ liệu đã thêm
SELECT * FROM PhuongThucVanChuyen ORDER BY ThuTu;
