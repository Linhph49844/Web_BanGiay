package com.example.Wedsite_bangiay.Controller;

import com.example.Wedsite_bangiay.model.SanPham;
import com.example.Wedsite_bangiay.repository.KhoHangRepository;
import com.example.Wedsite_bangiay.service.KhoHangService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  // Sử dụng đúng import
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("nhanvien")
public class NhanVienController {
    private final KhoHangService khoHangService;
    private final KhoHangRepository khoHangRepository;

    public NhanVienController(KhoHangService khoHangService, KhoHangRepository khoHangRepository) {
        this.khoHangService = khoHangService;
        this.khoHangRepository = khoHangRepository;
    }

    @GetMapping("hien-thi")
    public String hienThi(Model model) {
        return "nhanvien";
    }

    @GetMapping("don-hang")
    public String xacnhan(Model model) {
        return "xacnhandonhang";
    }

    @GetMapping("kho-hang")
    public String khohang(Model model) {
        List<SanPham> listSanPham = khoHangService.getAllSanPham();
        model.addAttribute("listSanPham", listSanPham);
        return "khohang";
    }
    // Sửa lại phương thức tìm kiếm
    @GetMapping("timkiem")
    public String searchProducts(@RequestParam("query") String query, Model model) {
        List<SanPham> sanPhamList = khoHangRepository.findByNameContaining(query);
        model.addAttribute("listSanPham", sanPhamList);
        return "khohang";  // Trả về view khohang với danh sách sản phẩm tìm được
    }
}