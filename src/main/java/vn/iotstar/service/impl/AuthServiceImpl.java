package vn.iotstar.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.iotstar.entity.DiaChi;
import vn.iotstar.entity.NguoiDung;
import vn.iotstar.entity.VaiTro;
import vn.iotstar.model.ApiResponse;
import vn.iotstar.model.LoginModel;
import vn.iotstar.model.NguoiDungModel;
import vn.iotstar.repository.NguoiDungRepository;
import vn.iotstar.repository.VaiTroRepository;
import vn.iotstar.service.AuthService;
import vn.iotstar.service.DiaChiService;
import vn.iotstar.util.JwtUtil;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private NguoiDungRepository nguoiDungRepository;

    @Autowired
    private VaiTroRepository vaiTroRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private DiaChiService diaChiService;

    @Override
    public ApiResponse<String> authenticateUser(LoginModel loginModel) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginModel.getEmail(), loginModel.getMatKhau())
            );
            
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtil.generateJwtToken(authentication);
            
            return ApiResponse.success(jwt);
        } catch (Exception e) {
            return ApiResponse.error("Email hoặc mật khẩu không đúng");
        }
    }

    @Override
    @Transactional
    public ApiResponse<String> registerUser(NguoiDungModel signUpModel) {
        if (nguoiDungRepository.existsByEmail(signUpModel.getEmail())) {
            return ApiResponse.error("Email đã được sử dụng");
        }

        NguoiDung user = new NguoiDung();
        user.setTenNguoiDung(signUpModel.getTenNguoiDung());
        user.setEmail(signUpModel.getEmail());
        user.setMatKhau(passwordEncoder.encode(signUpModel.getMatKhau()));
        user.setSdt(signUpModel.getSdt());
        user.setDiaChi(signUpModel.getDiaChi());
        user.setTrangThai("Hoạt động");

        Optional<VaiTro> userRole = vaiTroRepository.findById("USER");
        if (userRole.isEmpty()) {
            return ApiResponse.error("Vai trò mặc định không tồn tại");
        }
        user.setVaiTro(userRole.get());

        // Lưu người dùng
        NguoiDung savedUser = nguoiDungRepository.save(user);
        
        // Tự động tạo địa chỉ mặc định từ thông tin đăng ký
        if (signUpModel.getDiaChi() != null && !signUpModel.getDiaChi().trim().isEmpty() &&
            signUpModel.getSdt() != null && !signUpModel.getSdt().trim().isEmpty()) {
            try {
                DiaChi diaChiMacDinh = DiaChi.builder()
                    .nguoiDung(savedUser)
                    .tenNguoiNhan(signUpModel.getTenNguoiDung())
                    .soDienThoai(signUpModel.getSdt())
                    .diaChiChiTiet(signUpModel.getDiaChi())
                    .macDinh(true)
                    .trangThai("Hoạt động")
                    .build();
                diaChiService.save(diaChiMacDinh);
            } catch (Exception e) {
                // Không làm gián đoạn quá trình đăng ký nếu tạo địa chỉ thất bại
                System.err.println("Lỗi khi tạo địa chỉ mặc định: " + e.getMessage());
            }
        }
        
        return ApiResponse.success("Đăng ký người dùng thành công");
    }

    @Override
    public boolean validateUser(String email, String password) {
        try {
            Optional<NguoiDung> userOpt = nguoiDungRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                NguoiDung user = userOpt.get();
                return passwordEncoder.matches(password, user.getMatKhau()) && 
                       "Hoạt động".equals(user.getTrangThai());
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}