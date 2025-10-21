package vn.iotstar.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.iotstar.entity.DiaChi;
import vn.iotstar.entity.NguoiDung;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiaChiRepository extends JpaRepository<DiaChi, Integer> {
    
    List<DiaChi> findByNguoiDungAndTrangThai(NguoiDung nguoiDung, String trangThai);
    
    Optional<DiaChi> findByNguoiDungAndMacDinhAndTrangThai(NguoiDung nguoiDung, Boolean macDinh, String trangThai);
    
    List<DiaChi> findByNguoiDung(NguoiDung nguoiDung);
}
