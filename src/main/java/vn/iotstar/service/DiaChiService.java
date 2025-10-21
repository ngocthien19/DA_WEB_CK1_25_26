package vn.iotstar.service;

import vn.iotstar.entity.DiaChi;
import vn.iotstar.entity.NguoiDung;

import java.util.List;
import java.util.Optional;

public interface DiaChiService {
    
    List<DiaChi> findByNguoiDung(NguoiDung nguoiDung);
    
    List<DiaChi> findActiveByNguoiDung(NguoiDung nguoiDung);
    
    Optional<DiaChi> findDefaultByNguoiDung(NguoiDung nguoiDung);
    
    Optional<DiaChi> findById(Integer id);
    
    DiaChi save(DiaChi diaChi);
    
    void delete(Integer id);
    
    void setDefaultAddress(Integer addressId, NguoiDung nguoiDung);
}
