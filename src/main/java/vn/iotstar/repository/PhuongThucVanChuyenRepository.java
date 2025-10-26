package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.iotstar.entity.PhuongThucVanChuyen;

import java.util.List;

@Repository
public interface PhuongThucVanChuyenRepository extends JpaRepository<PhuongThucVanChuyen, Integer> {
    
    // Lấy tất cả phương thức vận chuyển đang hoạt động
    List<PhuongThucVanChuyen> findByTrangThaiTrueOrderByThuTuAsc();
    
    // Lấy tất cả phương thức vận chuyển sắp xếp theo thứ tự
    List<PhuongThucVanChuyen> findAllByOrderByThuTuAsc();
    
    // Đếm số phương thức vận chuyển đang hoạt động
    long countByTrangThaiTrue();
}
