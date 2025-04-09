package com.example.Wedsite_bangiay.Controller;

import com.example.Wedsite_bangiay.model.AccKhachHang;
import com.example.Wedsite_bangiay.model.AccNhanVien;
import com.example.Wedsite_bangiay.model.DanhMuc;
import com.example.Wedsite_bangiay.model.SanPham;
import com.example.Wedsite_bangiay.service.AccKhachHangService;
import com.example.Wedsite_bangiay.service.AccNhanVienService;
import com.example.Wedsite_bangiay.service.DanhMucService;
import com.example.Wedsite_bangiay.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("quanli")
public class AdminController {

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private DanhMucService danhMucService;

    @Autowired
    private AccNhanVienService accNhanVienService;

    @Autowired
    private AccKhachHangService accKhachHangService;

    @GetMapping("hien-thi")
    public String hienThi(Model model) {
        return "admin";  // Trang quản lý admin
    }

    // Hiển thị tất cả sản phẩm
    @GetMapping("/san-pham")
    public String sanpham(@RequestParam(value = "query", required = false) String query, Model model) {
        if (query != null && !query.isEmpty()) {
            model.addAttribute("listSanPham", sanPhamService.searchSanPham(query));  // Tìm kiếm sản phẩm
        } else {
            model.addAttribute("listSanPham", sanPhamService.getAllSanPham());  // Nếu không có tìm kiếm, hiển thị tất cả sản phẩm
        }
        model.addAttribute("listDanhMuc", danhMucService.getAllDanhMuc());
        return "quanlisanpham";  // Trang danh sách sản phẩm
    }

    // Hiển thị form thêm sản phẩm
    @GetMapping("/san-pham/them")
    public String showAddSanPham(Model model) {
        model.addAttribute("sanPham", new SanPham());  // Thêm đối tượng trống vào model để tạo form
        model.addAttribute("listDanhMuc", danhMucService.getAllDanhMuc());
        return "themsanpham";  // Tên view để hiển thị form thêm sản phẩm
    }

    @PostMapping("/san-pham/add")
    public String addSanPham(@ModelAttribute SanPham sanPham, @RequestParam("category_id") Long categoryId, Model model) {
        // Lấy danh mục từ categoryId
        Optional<DanhMuc> danhMucOptional = danhMucService.getDanhMucById(categoryId);

        if (!danhMucOptional.isPresent()) {
            model.addAttribute("error", "Danh mục không hợp lệ!");
            return "themsanpham";  // Nếu danh mục không tìm thấy, quay lại form thêm sản phẩm
        }

        DanhMuc danhMuc = danhMucOptional.get();

        // Gán danh mục vào sản phẩm
        sanPham.setDanhMuc(danhMuc);

        // Thêm sản phẩm vào cơ sở dữ liệu
        sanPhamService.addSanPham(sanPham);
        return "redirect:/quanli/san-pham";  // Quay lại trang quản lý sản phẩm sau khi thêm
    }

    // Hiển thị form sửa sản phẩm
    @GetMapping("/san-pham/edit/{id}")
    public String editSanPham(@PathVariable("id") Long id, Model model) {
        SanPham sanPham = sanPhamService.getSanPhamById(id).orElse(null);
        if (sanPham == null) {
            model.addAttribute("error", "Sản phẩm không tồn tại!");
            return "quanlisanpham";  // Quay lại trang sản phẩm nếu không tìm thấy sản phẩm
        }
        model.addAttribute("sanPham", sanPham);  // Gửi sản phẩm hiện tại tới form
        model.addAttribute("listDanhMuc", danhMucService.getAllDanhMuc());  // Gửi danh mục để hiển thị
        return "suasanpham";  // Tên view của form sửa sản phẩm
    }

    // Cập nhật sản phẩm
    @PostMapping("/san-pham/update/{id}")
    public String updateSanPham(@PathVariable("id") Long id,
                                @ModelAttribute SanPham sanPham,
                                @RequestParam("category_id") Long categoryId,
                                Model model) {

        SanPham existingSanPham = sanPhamService.getSanPhamById(id).orElse(null);
        if (existingSanPham == null) {
            model.addAttribute("error", "Sản phẩm không tồn tại!");
            return "redirect:/quanli/san-pham";
        }

        // Lấy danh mục từ categoryId
        Optional<DanhMuc> danhMucOptional = danhMucService.getDanhMucById(categoryId);
        if (!danhMucOptional.isPresent()) {
            model.addAttribute("error", "Danh mục không hợp lệ!");
            return "suasanpham";  // Nếu danh mục không hợp lệ, quay lại form sửa sản phẩm
        }

        DanhMuc danhMuc = danhMucOptional.get();

        // Gán danh mục mới vào sản phẩm
        sanPham.setDanhMuc(danhMuc);

        // Cập nhật thông tin sản phẩm, bao gồm cả danh mục
        sanPham.setId(id);  // Đảm bảo ID của sản phẩm không bị thay đổi
        sanPhamService.updateSanPham(id, sanPham);

        return "redirect:/quanli/san-pham";  // Quay lại trang quản lý sản phẩm sau khi cập nhật
    }

    // Xóa sản phẩm
    @GetMapping("/san-pham/delete/{id}")
    public String deleteSanPham(@PathVariable("id") Long id, Model model) {
        boolean isDeleted = sanPhamService.deleteSanPham(id);  // Xóa sản phẩm khỏi cơ sở dữ liệu
        if (!isDeleted) {
            model.addAttribute("error", "Sản phẩm không tồn tại hoặc không thể xóa!");
        }
        return "redirect:/quanli/san-pham";  // Quay lại trang sản phẩm sau khi xóa
    }

    // Tìm kiếm sản phẩm
    @GetMapping("/search")
    public String searchSanPham(@RequestParam("query") String query, Model model) {
        model.addAttribute("listSanPham", sanPhamService.searchSanPham(query));
        return "redirect:/quanli/san-pham"; // Hiển thị kết quả tìm kiếm
    }

    // Hiển thị danh sách nhân viên
    @GetMapping("/nhan-vien")
    public String showNhanVienList(@RequestParam(value = "query", required = false) String query, Model model) {
        if (query != null && !query.isEmpty()) {
            // Thực hiện tìm kiếm theo query
            model.addAttribute("listNhanVien", accNhanVienService.searchNhanVien(query));
        } else {
            // Hiển thị tất cả nhân viên nếu không có query
            model.addAttribute("listNhanVien", accNhanVienService.getAllNhanVien());
        }
        return "quanlinhanvien"; // Trả về view danh sách nhân viên
    }

    // Hiển thị form thêm nhân viên
    @GetMapping("/nhan-vien/them")
    public String showAddNhanVien(Model model) {
        model.addAttribute("nhanVien", new AccNhanVien()); // Tạo đối tượng nhân viên mới trong model
        return "themnhanvien";  // Tên view để hiển thị form thêm nhân viên
    }

    // Thêm nhân viên
    @PostMapping("/nhan-vien/add")
    public String addNhanVien(@ModelAttribute AccNhanVien accNhanVien, Model model) {
        accNhanVienService.saveNhanVien(accNhanVien); // Lưu nhân viên mới vào database
        return "redirect:/quanli/nhan-vien"; // Quay lại trang danh sách nhân viên
    }

    @GetMapping("/nhan-vien/edit/{id}")
    public String editNhanVien(@PathVariable("id") Long id, Model model) {
        Optional<AccNhanVien> accNhanVien = accNhanVienService.getNhanVienById(id);
        if (accNhanVien.isPresent()) {
            model.addAttribute("nhanVien", accNhanVien.get());  // Thêm nhanVien vào model
        } else {
            model.addAttribute("error", "Nhân viên không tồn tại!");
        }
        return "suanhanvien";  // View cho form sửa nhân viên
    }

    // Kiểm tra xem bạn đã có một phương thức này chưa
    @PostMapping("/nhan-vien/update/{id}")
    public String updateNhanVien(@PathVariable("id") Long id, @ModelAttribute AccNhanVien accNhanVien, Model model) {
        accNhanVien.setId(id);  // Đảm bảo ID của nhân viên không thay đổi
        accNhanVienService.saveNhanVien(accNhanVien);  // Cập nhật thông tin nhân viên
        return "redirect:/quanli/nhan-vien";  // Quay lại trang danh sách nhân viên
    }

    // Hiển thị danh sách khách hàng
    @GetMapping("khach-hang")
    public String showKhachHangList(@RequestParam(value = "query", required = false) String query, Model model) {
        if (query != null && !query.isEmpty()) {
            model.addAttribute("listKhachHang", accKhachHangService.searchKhachHang(query));
        } else {
            model.addAttribute("listKhachHang", accKhachHangService.getAllKhachHang());
        }
        return "quanlikhachhang";  // Trả về view danh sách khách hàng
    }

    // Hiển thị form sửa khách hàng
    @GetMapping("/khach-hang/edit/{id}")
    public String editKhachHang(@PathVariable("id") Long id, Model model) {
        Optional<AccKhachHang> accKhachHang = accKhachHangService.getKhachHangById(id);
        if (accKhachHang.isPresent()) {
            model.addAttribute("khachHang", accKhachHang.get());  // Truyền đối tượng khách hàng vào model
        } else {
            model.addAttribute("error", "Khách hàng không tồn tại!");
        }
        return "suakhachhang";  // Trả về view sửa khách hàng
    }

    // Cập nhật thông tin khách hàng
    @PostMapping("/khach-hang/update/{id}")
    public String updateKhachHang(@PathVariable("id") Long id, @ModelAttribute AccKhachHang accKhachHang, Model model) {
        accKhachHang.setId(id);  // Đảm bảo ID của khách hàng không thay đổi
        accKhachHangService.saveKhachHang(accKhachHang);  // Cập nhật thông tin khách hàng
        return "redirect:/quanli/khach-hang";  // Quay lại trang danh sách khách hàng
    }

    @GetMapping("doanh-thu")
    public String doanhthu(Model model) {
        return "doanhthu";  // Trang quản lý doanh thu
    }
}
