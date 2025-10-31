package vn.iotstar.controller.vendor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import vn.iotstar.entity.*;
import vn.iotstar.repository.*;
import vn.iotstar.service.DatHangService; 
import vn.iotstar.service.SanPhamService; 
import vn.iotstar.service.impl.UserDetailsServiceImpl;

@Controller
@RequestMapping("/vendor")
public class VendorDashboardController {

    @Autowired
    private NguoiDungRepository nguoiDungRepository; 

    @Autowired
    private SanPhamRepository sanPhamRepository; 
    
    @Autowired
    private CuaHangRepository cuaHangRepository;

    @Autowired
    private SanPhamService sanPhamService; 

    @Autowired
    private DatHangService datHangService; 
    
    private static final String STORE_LOCKED_MESSAGE = "Cửa hàng của bạn đã bị Admin khóa (Ngừng hoạt động). Vui lòng liên hệ Admin để được hỗ trợ.";


    /**
     * Hiển thị trang Vendor Dashboard chính
     */
    @GetMapping("/dashboard")
    public String showVendorDashboard(Model model) {
        try {
            // 1. KIỂM TRA QUYỀN VÀ LẤY USER
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication == null || !authentication.isAuthenticated() || 
                authentication instanceof AnonymousAuthenticationToken) {
                return "redirect:/login?error=unauthorized";
            }
            
            String email = authentication.getName();
            NguoiDung user = nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            if (!"VENDOR".equals(user.getVaiTro().getMaVaiTro())) {
                return "redirect:/register-store?error=not_a_vendor";
            }
            
            // 2. LẤY CỬA HÀNG CỦA VENDOR
            List<CuaHang> cuaHangList = cuaHangRepository.findByNguoiDung(user);
            if (cuaHangList.isEmpty()) {
                return "redirect:/register-store?error=store_not_found"; 
            }
            
            CuaHang cuaHang = cuaHangList.get(0); 

            // 3. THỐNG KÊ DỮ LIỆU
            
            // Sản phẩm
            List<SanPham> activeProductsList = sanPhamService.findByCuaHangAndTrangThai(cuaHang, true);
            List<SanPham> inactiveProductsList = sanPhamService.findByCuaHangAndTrangThai(cuaHang, false);
            long totalActiveProducts = activeProductsList.size();
            long totalInactiveProducts = inactiveProductsList.size();
            
            // ĐƠN HÀNG - FIX: Dùng trạng thái tiếng Việt có dấu
            long newOrdersCount = datHangService.countByCuaHangAndTrangThai(cuaHang, "Chờ xác nhận");
            long completedOrdersCount = datHangService.countByCuaHangAndTrangThai(cuaHang, "Hoàn thành");
            
            // LẤY DANH SÁCH GẦN ĐÂY - FIX: Dùng trạng thái tiếng Việt có dấu
            List<DatHang> recentNewOrders = datHangService.findRecentOrdersByCuaHangAndTrangThai(cuaHang, "Chờ xác nhận", 5);
            List<SanPham> recentProducts = sanPhamRepository.findByCuaHang(cuaHang, 
                PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "ngayNhap"))).getContent();


         // 4. KIỂM TRA VÀ TRUYỀN THÔNG BÁO LỖI
            if (cuaHang.getTrangThai() == false) {
                String errorMessage = STORE_LOCKED_MESSAGE;
                if (cuaHang.getLyDoKhoa() != null && !cuaHang.getLyDoKhoa().trim().isEmpty()) {
                    errorMessage += "\nLý do: " + cuaHang.getLyDoKhoa();
                }
                model.addAttribute("errorMessage", errorMessage);
            }


            // 5. THÊM DỮ LIỆU VÀO MODEL
            model.addAttribute("user", user);
            model.addAttribute("cuaHang", cuaHang);
            model.addAttribute("totalActiveProducts", totalActiveProducts);
            model.addAttribute("totalInactiveProducts", totalInactiveProducts);
            model.addAttribute("newOrdersCount", newOrdersCount);
            model.addAttribute("completedOrdersCount", completedOrdersCount);
            model.addAttribute("recentNewOrders", recentNewOrders);
            model.addAttribute("recentProducts", recentProducts);
            
            // Kiểm tra và hiển thị Toast Message từ RedirectAttributes (nếu có)
            if (model.containsAttribute("toastMessage")) {
                model.addAttribute("toastMessage", model.asMap().get("toastMessage"));
            }
            
            return "vendor/vendor-dashboard";
            
        } catch (Exception e) {
            System.err.println("Error loading vendor dashboard: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/login?error=system_error";
        }
    }

    /**
     * Redirect đến trang quản lý sản phẩm
     */
    @GetMapping("/products-redirect")
    public String redirectToProductManagement() {
        return "redirect:/vendor/products";
    }

    /**
     * Redirect đến trang quản lý đơn hàng
     */
    @GetMapping("/orders-redirect")
    public String redirectToOrderManagement() {
        return "redirect:/vendor/orders";
    }

    /**
     * Redirect đến trang quản lý khuyến mãi
     */
    @GetMapping("/promotions-redirect")
    public String redirectToPromotionManagement() {
        return "redirect:/vendor/promotions";
    }

    /**
     * Redirect đến trang quản lý vận chuyển
     */
    @GetMapping("/delivery-redirect")
    public String redirectToDeliveryManagement() {
        return "redirect:/vendor/delivery";
    }
    
    /**
     * Hiển thị trang tin nhắn (Messages)
     */
    @GetMapping("/messages")
    public String showMessages(Model model, jakarta.servlet.http.HttpSession session) {
        try {
            // Kiểm tra quyền và lấy user
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            
            if (authentication == null || !authentication.isAuthenticated() || 
                authentication instanceof AnonymousAuthenticationToken) {
                return "redirect:/login?error=unauthorized";
            }
            
            String email = authentication.getName();
            NguoiDung user = nguoiDungRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
            
            if (!"VENDOR".equals(user.getVaiTro().getMaVaiTro())) {
                return "redirect:/register-store?error=not_a_vendor";
            }
            
            // Lấy cửa hàng của vendor
            List<CuaHang> cuaHangList = cuaHangRepository.findByNguoiDung(user);
            if (cuaHangList.isEmpty()) {
                return "redirect:/register-store?error=store_not_found"; 
            }
            
            CuaHang cuaHang = cuaHangList.get(0);
            
            // IMPORTANT: Store userId in session for WebSocket
            session.setAttribute("userId", user.getMaNguoiDung());
            System.out.println("🔑 Stored vendor userId in session: " + user.getMaNguoiDung());
            
            // Kiểm tra trạng thái cửa hàng
            if (cuaHang.getTrangThai() == false) {
                model.addAttribute("errorMessage", STORE_LOCKED_MESSAGE);
            }
            
            model.addAttribute("user", user);  // Thêm user cho header
            model.addAttribute("vendor", user);
            model.addAttribute("cuaHang", cuaHang);
            
            return "vendor/messages";
            
        } catch (Exception e) {
            System.err.println("Error loading messages page: " + e.getMessage());
            e.printStackTrace();
            return "redirect:/vendor/dashboard?error=system_error";
        }
    }
}