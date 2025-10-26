package vn.iotstar.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import vn.iotstar.entity.NguoiDung;
import vn.iotstar.entity.PhuongThucVanChuyen;
import vn.iotstar.service.NguoiDungService;
import vn.iotstar.service.PhuongThucVanChuyenService;
import vn.iotstar.service.UserDetailsImpl;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/admin/shipping-methods")
@RequiredArgsConstructor
public class AdminPhuongThucVanChuyenController {

    private final PhuongThucVanChuyenService phuongThucVanChuyenService;
    private final NguoiDungService nguoiDungService;

    // Hiển thị danh sách phương thức vận chuyển
    @GetMapping
    public String listShippingMethods(Model model) {
        // Lấy thông tin user đang đăng nhập
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            NguoiDung user = nguoiDungService.getUserByEmail(userDetails.getUsername()).orElse(null);
            model.addAttribute("user", user);
        }
        
        List<PhuongThucVanChuyen> shippingMethods = phuongThucVanChuyenService.findAll();
        model.addAttribute("shippingMethods", shippingMethods);
        model.addAttribute("activeCount", phuongThucVanChuyenService.countActiveShippingMethods());
        return "admin/shipping-methods/list";
    }

    // Hiển thị form thêm mới
    @GetMapping("/add")
    public String showAddForm(Model model) {
        // Lấy thông tin user đang đăng nhập
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            NguoiDung user = nguoiDungService.getUserByEmail(userDetails.getUsername()).orElse(null);
            model.addAttribute("user", user);
        }
        
        model.addAttribute("shippingMethod", new PhuongThucVanChuyen());
        model.addAttribute("isEdit", false);
        return "admin/shipping-methods/form";
    }

    // Xử lý thêm mới
    @PostMapping("/add")
    public String addShippingMethod(@Valid @ModelAttribute("shippingMethod") PhuongThucVanChuyen phuongThucVanChuyen,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", false);
            return "admin/shipping-methods/form";
        }

        try {
            phuongThucVanChuyenService.save(phuongThucVanChuyen);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm phương thức vận chuyển thành công!");
            return "redirect:/admin/shipping-methods";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            model.addAttribute("isEdit", false);
            return "admin/shipping-methods/form";
        }
    }

    // Hiển thị form chỉnh sửa
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model, RedirectAttributes redirectAttributes) {
        // Lấy thông tin user đang đăng nhập
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            NguoiDung user = nguoiDungService.getUserByEmail(userDetails.getUsername()).orElse(null);
            model.addAttribute("user", user);
        }
        
        return phuongThucVanChuyenService.findById(id)
            .map(shippingMethod -> {
                model.addAttribute("shippingMethod", shippingMethod);
                model.addAttribute("isEdit", true);
                return "admin/shipping-methods/form";
            })
            .orElseGet(() -> {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy phương thức vận chuyển!");
                return "redirect:/admin/shipping-methods";
            });
    }

    // Xử lý cập nhật
    @PostMapping("/edit/{id}")
    public String updateShippingMethod(@PathVariable Integer id,
                                       @Valid @ModelAttribute("shippingMethod") PhuongThucVanChuyen phuongThucVanChuyen,
                                       BindingResult result,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("isEdit", true);
            return "admin/shipping-methods/form";
        }

        try {
            phuongThucVanChuyenService.update(id, phuongThucVanChuyen);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật phương thức vận chuyển thành công!");
            return "redirect:/admin/shipping-methods";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
            model.addAttribute("isEdit", true);
            return "admin/shipping-methods/form";
        }
    }

    // Xóa phương thức vận chuyển
    @PostMapping("/delete/{id}")
    public String deleteShippingMethod(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            phuongThucVanChuyenService.delete(id);
            redirectAttributes.addFlashAttribute("successMessage", "Xóa phương thức vận chuyển thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Không thể xóa: " + e.getMessage());
        }
        return "redirect:/admin/shipping-methods";
    }

    // Thay đổi trạng thái
    @PostMapping("/toggle-status/{id}")
    public String toggleStatus(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            phuongThucVanChuyenService.toggleStatus(id);
            redirectAttributes.addFlashAttribute("successMessage", "Cập nhật trạng thái thành công!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Có lỗi xảy ra: " + e.getMessage());
        }
        return "redirect:/admin/shipping-methods";
    }
}
