package com.example.Wedsite_bangiay.Controller;

import com.example.Wedsite_bangiay.model.AccKhachHang;
import com.example.Wedsite_bangiay.model.GioHang;
import com.example.Wedsite_bangiay.model.SanPham;
import com.example.Wedsite_bangiay.model.DonHang;
import com.example.Wedsite_bangiay.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("thanhtoan")
public class ThanhtoanController {

    @Autowired
    private GioHangService gioHangService;  // Sử dụng GioHangService để xử lý giỏ hàng

    @Autowired
    private SanPhamService sanPhamService;  // Để cập nhật số lượng sản phẩm trong kho

    @Autowired
    private DonHangService donHangService; // Dịch vụ cho đơn hàng

    @Autowired
    private XacNhanDonHangService xacNhanDonHangService;

    @Autowired
    private AccKhachHangService accKhachHangService;// Dịch vụ xác nhận đơn hàng

    @PostMapping("/thanh-toan")
    public String thanhToan(@RequestParam("paymentMethod") String paymentMethod,
                            @RequestParam("userId") Long userId,
                            Model model) throws Exception {
        // Lấy giỏ hàng của người dùng từ service
        List<GioHang> gioHangs = gioHangService.getGioHangByUserId(userId);
        if (gioHangs.isEmpty()) {
            return "redirect:/giay/gio-hang";  // Trở lại giỏ hàng nếu không có giỏ hàng
        }

        // Tính tổng tiền giỏ hàng
        BigDecimal totalAmount = gioHangs.stream()
                .map(item -> item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("products", gioHangs); // Lấy danh sách sản phẩm từ giỏ hàng

        // Tạo đơn hàng mới
        DonHang donHang = new DonHang();
        Optional<AccKhachHang> accKhachHangOpt = accKhachHangService.getKhachHangById(userId);
        if (accKhachHangOpt.isPresent()) {
            donHang.setAccKhachHang(accKhachHangOpt.get());  // Gán đối tượng người dùng vào đơn hàng
            donHang.setTotalAmount(totalAmount);  // Tổng tiền giỏ hàng
            donHang.setStatus("Đang chờ xác nhận");
            donHangService.saveDonHang(donHang);  // Lưu đơn hàng

            // Gửi thông tin đơn hàng cho nhân viên để xác nhận
            xacNhanDonHangService.createXacNhanDonHang(donHang);
        }

        if (paymentMethod.equals("QR")) {
            // Hiển thị thông tin thanh toán qua QR
            return "thanhtoanqr";  // Trả về trang QR
        } else if (paymentMethod.equals("COD")) {
            // Quay về trang thông báo thành công COD
            return "redirect:/thanhtoan/thanh-toan-cod";
        }

        return "redirect:/giay/gio-hang";  // Quay lại giỏ hàng nếu không thành công
    }


    // Trang thanh toán thành công (COD)
    @GetMapping("/thanh-toan-cod")
    public String thanhToanCodSuccess() {
        return "thongbaocod";  // Trang thông báo đặt hàng thành công (COD)
    }

    @PostMapping("/thanh-toan-thanh-cong")
    public String thanhToanThanhCong(@RequestParam("userId") Long userId, Model model) {
        // Lấy giỏ hàng của người dùng
        List<GioHang> gioHangs = gioHangService.getGioHangByUserId(userId);
        if (gioHangs.isEmpty()) {
            return "redirect:/giay/gio-hang";  // Trở lại giỏ hàng nếu không có giỏ hàng
        }

        // Tính tổng tiền giỏ hàng
        BigDecimal totalAmount = gioHangs.stream()
                .map(item -> item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("totalAmount", totalAmount);
        model.addAttribute("products", gioHangs); // Lấy danh sách sản phẩm từ giỏ hàng

        // Cập nhật số lượng sản phẩm trong kho và xóa giỏ hàng
        for (GioHang gioHang : gioHangs) {
            SanPham sanPham = gioHang.getProduct();
            sanPham.setQuantity(sanPham.getQuantity() - gioHang.getQuantity());  // Giảm số lượng sản phẩm trong kho
            sanPhamService.addSanPham(sanPham);  // Lưu lại sản phẩm đã cập nhật
        }

        // Xóa tất cả giỏ hàng sau khi thanh toán thành công
        gioHangService.xoaToanBoGioHang(userId);  // Gọi phương thức xóa toàn bộ giỏ hàng

        model.addAttribute("message", "Thanh toán thành công!");
        return "thongbaocod";  // Trang thông báo thanh toán thành công
    }
}
