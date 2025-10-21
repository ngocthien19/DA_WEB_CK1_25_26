package vn.iotstar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.iotstar.entity.DiaChi;
import vn.iotstar.entity.NguoiDung;
import vn.iotstar.repository.DiaChiRepository;
import vn.iotstar.service.DiaChiService;

import java.util.List;
import java.util.Optional;

@Service
public class DiaChiServiceImpl implements DiaChiService {
    
    @Autowired
    private DiaChiRepository diaChiRepository;
    
    @Override
    public List<DiaChi> findByNguoiDung(NguoiDung nguoiDung) {
        return diaChiRepository.findByNguoiDung(nguoiDung);
    }
    
    @Override
    public List<DiaChi> findActiveByNguoiDung(NguoiDung nguoiDung) {
        return diaChiRepository.findByNguoiDungAndTrangThai(nguoiDung, "Hoạt động");
    }
    
    @Override
    public Optional<DiaChi> findDefaultByNguoiDung(NguoiDung nguoiDung) {
        return diaChiRepository.findByNguoiDungAndMacDinhAndTrangThai(nguoiDung, true, "Hoạt động");
    }
    
    @Override
    public Optional<DiaChi> findById(Integer id) {
        return diaChiRepository.findById(id);
    }
    
    @Override
    @Transactional
    public DiaChi save(DiaChi diaChi) {
        // Nếu địa chỉ này được đặt là mặc định, bỏ mặc định các địa chỉ khác
        if (diaChi.getMacDinh() != null && diaChi.getMacDinh()) {
            List<DiaChi> otherAddresses = diaChiRepository.findByNguoiDung(diaChi.getNguoiDung());
            for (DiaChi addr : otherAddresses) {
                if (!addr.getMaDiaChi().equals(diaChi.getMaDiaChi()) && addr.getMacDinh()) {
                    addr.setMacDinh(false);
                    diaChiRepository.save(addr);
                }
            }
        }
        return diaChiRepository.save(diaChi);
    }
    
    @Override
    @Transactional
    public void delete(Integer id) {
        Optional<DiaChi> diaChi = diaChiRepository.findById(id);
        if (diaChi.isPresent()) {
            DiaChi addr = diaChi.get();
            addr.setTrangThai("Đã xóa");
            diaChiRepository.save(addr);
        }
    }
    
    @Override
    @Transactional
    public void setDefaultAddress(Integer addressId, NguoiDung nguoiDung) {
        // Bỏ mặc định tất cả địa chỉ của người dùng
        List<DiaChi> addresses = diaChiRepository.findByNguoiDung(nguoiDung);
        for (DiaChi addr : addresses) {
            addr.setMacDinh(false);
            diaChiRepository.save(addr);
        }
        
        // Đặt địa chỉ được chọn làm mặc định
        Optional<DiaChi> selectedAddress = diaChiRepository.findById(addressId);
        if (selectedAddress.isPresent()) {
            DiaChi addr = selectedAddress.get();
            addr.setMacDinh(true);
            diaChiRepository.save(addr);
        }
    }
}
