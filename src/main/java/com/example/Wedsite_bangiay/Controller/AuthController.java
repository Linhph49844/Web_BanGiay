package com.example.Wedsite_bangiay.Controller;

import com.example.Wedsite_bangiay.model.AccKhachHang;
import com.example.Wedsite_bangiay.model.GioHang;
import com.example.Wedsite_bangiay.model.SanPham;
import com.example.Wedsite_bangiay.service.AccKhachHangService;
import com.example.Wedsite_bangiay.service.DanhMucService;
import com.example.Wedsite_bangiay.service.GioHangService;
import com.example.Wedsite_bangiay.service.SanPhamService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("giay")
public class AuthController {

    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private DanhMucService danhMucService;

    @Autowired
    private GioHangService gioHangService;

    @Autowired
    private AccKhachHangService accKhachHangService;

    // Hiển thị trang đăng nhập
    @GetMapping("dang-nhap")
    public String dangnhap(Model model) {
        return "dangnhap";
    }

    // Xử lý đăng nhập
    @PostMapping("dang-nhap")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        Model model) {
        // Kiểm tra tài khoản và mật khẩu
        AccKhachHang accKhachHang = accKhachHangService.findByUsernameAndPassword(username, password);

        if (accKhachHang != null) {
            // Đăng nhập thành công, lưu thông tin người dùng vào session
            session.setAttribute("user", accKhachHang);
            return "redirect:/giay/hien-thi"; // Chuyển hướng đến trang sản phẩm hoặc trang chủ
        } else {
            // Đăng nhập thất bại, thông báo lỗi
            model.addAttribute("error", "Sai tài khoản hoặc mật khẩu!");
            return "dangnhap"; // Quay lại trang đăng nhập
        }
    }

    @GetMapping("hien-thi")
    public String hienThi(Model model, HttpSession session) {
        // Lấy thông tin người dùng từ session
        AccKhachHang user = (AccKhachHang) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("username", user.getUsername()); // Truyền tên tài khoản vào model
        }
        return "hienthilogin"; // Trang hiển thị sau khi đăng nhập thành công
    }

    // Hiển thị trang sản phẩm
    @GetMapping("san-pham")
    public String sanpham(Model model, HttpSession session) {
        // Lấy thông tin người dùng từ session
        AccKhachHang user = (AccKhachHang) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("username", user.getUsername()); // Truyền tên tài khoản vào model
        }
        model.addAttribute("listSanPham", sanPhamService.getAllSanPham());
        model.addAttribute("listDanhMuc", danhMucService.getAllDanhMuc());
        return "sanphamlogin";
    }
    // Hiển thị sản phẩm theo danh mục
    @GetMapping("/danhmuc/{id}")
    public String sanPhamTheoDanhMuc(@PathVariable("id") Long idDanhMuc, Model model,HttpSession session) {
        AccKhachHang user = (AccKhachHang) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("username", user.getUsername()); // Truyền tên tài khoản vào model
        }
        model.addAttribute("listSanPham", sanPhamService.getSanPhamByDanhMuc(idDanhMuc));  // Lấy sản phẩm theo danh mục
        model.addAttribute("listDanhMuc", danhMucService.getAllDanhMuc());  // Lấy danh sách danh mục
        return "sanphamlogin";  // Tên view
    }

    // Tìm kiếm sản phẩm
    @GetMapping("/search")
    public String searchSanPham(@RequestParam("query") String query, Model model,HttpSession session) {
        AccKhachHang user = (AccKhachHang) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("username", user.getUsername()); // Truyền tên tài khoản vào model
        }
        model.addAttribute("listSanPham", sanPhamService.searchSanPham(query));
        return "sanphamlogin"; // Hiển thị kết quả tìm kiếm
    }

    @PostMapping("/giohang/them/{id}")
    public String themVaoGioHang(@PathVariable Long id, @RequestParam(defaultValue = "1") int quantity, HttpSession session) {
        // Lấy thông tin người dùng từ session
        AccKhachHang user = (AccKhachHang) session.getAttribute("user");
        if (user == null) {
            return "redirect:/giay/dang-nhap"; // Nếu người dùng chưa đăng nhập, chuyển đến trang đăng nhập
        }

        // Lấy thông tin sản phẩm từ database
        Optional<SanPham> sanPhamOptional = sanPhamService.getSanPhamById(id);
        if (sanPhamOptional.isPresent()) {
            SanPham sanPham = sanPhamOptional.get();

            // Kiểm tra nếu số lượng nhập vào hợp lệ
            if (quantity > sanPham.getQuantity()) {
                return "redirect:/sanpham/" + id; // Số lượng vượt quá tồn kho, quay lại trang sản phẩm
            }

            // Kiểm tra xem sản phẩm đã có trong giỏ hàng hay chưa
            Optional<GioHang> gioHangOptional = gioHangService.findByUserAndProduct(user.getId(), sanPham.getId());

            if (gioHangOptional.isPresent()) {
                // Nếu có rồi, cập nhật số lượng
                GioHang gioHang = gioHangOptional.get();
                gioHang.setQuantity(gioHang.getQuantity() + quantity); // Tăng số lượng lên theo số lượng người dùng nhập
                gioHangService.save(gioHang);
            } else {
                // Nếu chưa có, thêm sản phẩm mới vào giỏ hàng
                GioHang gioHang = new GioHang();
                gioHang.setUser(user);
                gioHang.setProduct(sanPham);
                gioHang.setQuantity(quantity); // Sử dụng số lượng người dùng nhập
                gioHangService.save(gioHang);
            }
        }

        return "redirect:/giay/gio-hang"; // Quay lại trang giỏ hàng
    }

    // Hiển thị giỏ hàng của người dùng
    @GetMapping("/gio-hang")
    public String hienThiGioHang(Model model, HttpSession session) {
        // Lấy thông tin người dùng từ session
        AccKhachHang user = (AccKhachHang) session.getAttribute("user");
        if (user == null) {
            return "redirect:/giay/dang-nhap";  // Nếu người dùng chưa đăng nhập, chuyển đến trang đăng nhập
        }

        // Lấy giỏ hàng của người dùng từ service
        List<GioHang> gioHangs = gioHangService.getGioHangByUserId(user.getId());

        // Truyền giỏ hàng và người dùng vào model
        model.addAttribute("gioHangs", gioHangs);
        model.addAttribute("user", user);

        return "giohang";  // Tên của trang giỏ hàng
    }

    @PostMapping("/gio-hang/cap-nhat/{gioHangId}")
    public String capNhatSoLuong(@PathVariable Long gioHangId,
                                 @RequestParam("quantity") int quantity,
                                 @RequestParam("userId") Long userId) {
        gioHangService.capNhatSoLuong(gioHangId, quantity);  // Cập nhật số lượng sản phẩm trong giỏ hàng
        return "redirect:/giay/gio-hang?userId=" + userId;  // Quay lại giỏ hàng của người dùng
    }

    // Xóa sản phẩm khỏi giỏ hàng
    @GetMapping("/gio-hang/xoa/{gioHangId}")
    public String xoaSanPham(@PathVariable Long gioHangId, @RequestParam("userId") Long userId) {
        gioHangService.xoaSanPhamFromGioHang(gioHangId);  // Xóa sản phẩm khỏi giỏ hàng
        return "redirect:/giay/gio-hang?userId=" + userId;  // Quay lại giỏ hàng của người dùng
    }

    // Hiển thị liên hệ
    @GetMapping("lien-he")
    public String lienhe(Model model, HttpSession session) {
        // Lấy thông tin người dùng từ session
        AccKhachHang user = (AccKhachHang) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("username", user.getUsername()); // Truyền tên tài khoản vào model
        }
        return "lienhelogin";
    }

    // Hiển thị lịch sử mua hàng
    @GetMapping("lich-su")
    public String lichsu(Model model, HttpSession session) {
        // Lấy thông tin người dùng từ session
        AccKhachHang user = (AccKhachHang) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("username", user.getUsername()); // Truyền tên tài khoản vào model
        }
        return "lichsumuahang";
    }
}

