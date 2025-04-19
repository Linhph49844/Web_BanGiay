package com.example.Wedsite_bangiay.Controller;

import com.example.Wedsite_bangiay.model.AccNhanVien;
import com.example.Wedsite_bangiay.model.GioHang;
import com.example.Wedsite_bangiay.model.SanPham;
import com.example.Wedsite_bangiay.model.XacNhanDonHang;
import com.example.Wedsite_bangiay.repository.KhoHangRepository;
import com.example.Wedsite_bangiay.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;  // Sử dụng đúng import
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("nhanvien")
public class NhanVienController {

    @Autowired
    private KhoHangService khoHangService;

    @Autowired
    private KhoHangRepository khoHangRepository;
    @Autowired
    private XacNhanDonHangService xacNhanDonHangService;

    @Autowired
    private AccNhanVienService accNhanVienService;

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private GioHangService gioHangService;

    // Hiển thị trang đăng nhập nhân viên
    @GetMapping("dang-nhap")
    public String dangNhapNhanVien(Model model) {
        return "dangnhapnhanvien";
    }

    // Xử lý đăng nhập nhân viên
    @PostMapping("dang-nhap")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        // Kiểm tra tài khoản và mật khẩu
        AccNhanVien accNhanVien = accNhanVienService.findByUsernameAndPassword(username, password);

        if (accNhanVien != null) {
            // Đăng nhập thành công, lưu thông tin người dùng vào session
            session.setAttribute("user", accNhanVien);
            return "redirect:/nhanvien/hien-thi"; // Chuyển hướng đến trang sản phẩm hoặc trang chủ
        } else {
            // Đăng nhập thất bại, thông báo lỗi
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            return "dangnhapnhanvien"; // Quay lại trang đăng nhập
        }
    }

    // Ví dụ cho một trang cần kiểm tra đăng nhập
    @GetMapping("hien-thi")
    public String hienThi(Model model, HttpSession session) {
        // Lấy thông tin nhân viên từ session
        AccNhanVien user = (AccNhanVien) session.getAttribute("user");

        // Kiểm tra nếu người dùng đã đăng nhập
        if (user != null) {
            // Nếu đã đăng nhập, thêm thông tin người dùng vào model để hiển thị trên trang
            model.addAttribute("username", user.getUsername());
            return "nhanvien";  // Trang hiển thị sau khi đăng nhập thành công
        } else {
            // Nếu chưa đăng nhập, chuyển hướng tới trang đăng nhập
            return "redirect:/nhanvien/dang-nhap";  // Chuyển hướng đến trang đăng nhập
        }


    }

    @GetMapping("don-hang")
    public String showOrderConfirmationPage(Model model, HttpSession session) {
        // Kiểm tra đăng nhập nhân viên
        AccNhanVien user = (AccNhanVien) session.getAttribute("user");
        if (user == null) {
            return "redirect:/nhanvien/dang-nhap";  // Chuyển hướng đến trang đăng nhập nếu nhân viên chưa đăng nhập
        }

        // Lấy tất cả đơn hàng từ tất cả khách hàng
        List<XacNhanDonHang> orders = xacNhanDonHangService.getAllXacNhanDonHang();

        // Phân loại đơn hàng chờ xác nhận và đã xác nhận
        List<XacNhanDonHang> pendingOrders = orders.stream()
                .filter(order -> "Chờ xác nhận".equals(order.getStatus()) || "Chờ xử lý".equals(order.getStatus()))
                .collect(Collectors.toList());

        List<XacNhanDonHang> confirmedOrders = orders.stream()
                .filter(order -> "Đã xác nhận".equals(order.getStatus()))
                .collect(Collectors.toList());

        // Kết hợp các đơn hàng chưa xác nhận lên đầu
        pendingOrders.addAll(confirmedOrders);

        // Thêm vào model để hiển thị
        model.addAttribute("gioHangs", pendingOrders);  // Truyền vào view danh sách đơn hàng

        return "xacnhandonhang";  // Trả về trang xác nhận đơn hàng
    }

    // Xác nhận đơn hàng
    @PostMapping("don-hang/{id}/confirm")
    public String confirmOrder(@PathVariable Long id, Model model) {
        XacNhanDonHang xacNhanDonHang = xacNhanDonHangService.confirmOrder(id);
        if (xacNhanDonHang != null) {
            // Sau khi xác nhận, trừ số lượng sản phẩm trong kho
            for (SanPham sanPham : xacNhanDonHang.getDonHang().getSanPhamList()) {
                sanPham.setQuantity(sanPham.getQuantity() - 1);  // Giảm số lượng sản phẩm
                sanPhamService.addSanPham(sanPham);  // Lưu lại thay đổi trong kho
            }
        }
        return "redirect:/nhanvien/don-hang";  // Quay lại danh sách đơn hàng
    }

    // Hủy xác nhận đơn hàng
    @PostMapping("don-hang/{id}/cancel")
    public String cancelOrder(@PathVariable Long id, Model model) {
        XacNhanDonHang xacNhanDonHang = xacNhanDonHangService.cancelOrder(id);
        if (xacNhanDonHang != null) {
            model.addAttribute("order", xacNhanDonHang);
        }
        return "redirect:/nhanvien/don-hang";  // Quay lại danh sách đơn hàng
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
