package com.example.Wedsite_bangiay.Controller;

import com.example.Wedsite_bangiay.model.*;
import com.example.Wedsite_bangiay.repository.ReviewsRepository;
import com.example.Wedsite_bangiay.service.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @Autowired
    private LichSuMuaHangService lichSuMuaHangService;

    @Autowired
    private ReviewsService reviewsService;

    // Hiển thị trang đăng ký
    @GetMapping("dang-ky")
    public String dangKy() {
        return "dangky"; // Trang đăng ký
    }

    @PostMapping("dang-ky")
    public String dangKy(@RequestParam("username") String username,
                         @RequestParam("password") String password,
                         @RequestParam("email") String email,
                         @RequestParam("sdt") String sdt,
                         @RequestParam("diachi") String diachi,
                         @RequestParam("name") String name,
                         Model model) {

        // Kiểm tra các trường yêu cầu không được để trống
        if (username.isEmpty() || password.isEmpty() || email.isEmpty() || sdt.isEmpty() || diachi.isEmpty() || name.isEmpty()) {
            model.addAttribute("error", "Tất cả các trường đều phải được điền đầy đủ!");
            return "dangky"; // Trở về trang đăng ký nếu có trường trống
        }

        // Tạo đối tượng AccKhachHang mới
        AccKhachHang accKhachHang = new AccKhachHang();
        accKhachHang.setUsername(username);
        accKhachHang.setPassword(password);  // Chú ý cần mã hóa mật khẩu nếu cần
        accKhachHang.setEmail(email);
        accKhachHang.setSdt(sdt);
        accKhachHang.setDiachi(diachi);
        accKhachHang.setName(name); // Set giá trị cho trường name

        // Lưu khách hàng vào cơ sở dữ liệu
        accKhachHangService.saveKhachHang(accKhachHang);

        return "redirect:/giay/dang-nhap"; // Chuyển hướng đến trang đăng nhập sau khi đăng ký thành công
    }

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

    // Hiển thị giỏ hàng của người dùng
    @GetMapping("/gio-hang")
    public String hienThiGioHang(Model model, HttpSession session) {
        // Lấy thông tin người dùng từ session
        AccKhachHang user = (AccKhachHang) session.getAttribute("user");
        if (user != null) {
            model.addAttribute("username", user.getUsername()); // Truyền tên tài khoản vào model
        }
        if (user == null) {
            return "redirect:/giay/dang-nhap";  // Nếu người dùng chưa đăng nhập, chuyển đến trang đăng nhập
        }

        // Lấy giỏ hàng của người dùng từ service
        List<GioHang> gioHangs = gioHangService.getGioHangByUserId(user.getId());

        // Tính tổng tiền của giỏ hàng
        BigDecimal totalAmount = gioHangs.stream()
                .map(item -> item.getProduct().getPrice().multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Truyền giỏ hàng, tổng tiền và người dùng vào model
        model.addAttribute("gioHangs", gioHangs);
        model.addAttribute("totalAmount", totalAmount);  // Truyền tổng tiền vào view
        model.addAttribute("user", user);

        return "giohang";  // Tên của trang giỏ hàng
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

    @GetMapping("/{id}/danhgia")
    public String hienThiDanhGia(@PathVariable("id") Long idSanPham, Model model) {
        SanPham sanPham = sanPhamService.getSanPhamById(idSanPham).orElse(null);
        List<Reviews> reviewsList = reviewsService.getReviewsByProductId(idSanPham);

        model.addAttribute("sanPham", sanPham);
        model.addAttribute("reviews", reviewsList);

        model.addAttribute("sanPham", sanPham);
        return "reviewslogin";  // Trả về view reviewslogin.html
    }

    // Hiển thị lịch sử mua hàng của người dùng
    @GetMapping("/lich-su-mua-hang")
    public String hienThiLichSuMuaHang(Model model, HttpSession session) {
        // Lấy thông tin người dùng từ session
        AccKhachHang user = (AccKhachHang) session.getAttribute("user");
        if (user == null) {
            return "redirect:/giay/dang-nhap";  // Nếu người dùng chưa đăng nhập, chuyển đến trang đăng nhập
        }

        // Lấy danh sách lịch sử mua hàng của người dùng
        List<LichSuMuaHang> lichSuMuaHangList = lichSuMuaHangService.getLichSuMuaHangByUserId(user.getId());

        // Truyền danh sách vào model
        model.addAttribute("lichSuMuaHangList", lichSuMuaHangList);

        return "lichsumuahang";  // Tên của view (HTML) để hiển thị danh sách
    }

    // Hiển thị form sửa đánh giá sản phẩm
    @GetMapping("/danhgiasanpham/{id}")
    public String danhGiaSanPham(@PathVariable("id") Long productId, HttpSession session, Model model) {
        // Lấy thông tin sản phẩm
        SanPham sanPham = sanPhamService.getSanPhamById(productId).orElse(null);

        // Kiểm tra xem người dùng đã đánh giá sản phẩm chưa
        AccKhachHang user = (AccKhachHang) session.getAttribute("user");
        if (user != null) {
            // Lấy đánh giá của người dùng nếu đã có
            Reviews review = reviewsService.getReviewByUserAndProduct(user.getId(), productId);
            if (review != null) {
                model.addAttribute("review", review);
            } else {
                // Nếu chưa có đánh giá, tạo một đối tượng review rỗng
                model.addAttribute("review", new Reviews());
            }
        }

        model.addAttribute("sanPham", sanPham);

        return "danhgiasanpham";  // Trang sửa đánh giá sản phẩm
    }

    // Xử lý sửa đánh giá sản phẩm
    @PostMapping("/ghi-danh-gia")
    public String saveReview(@RequestParam("productId") Long productId,
                             @RequestParam("comment") String comment,
                             HttpSession session) {
        // Lấy thông tin người dùng từ session
        AccKhachHang user = (AccKhachHang) session.getAttribute("user");

        if (user == null) {
            return "redirect:/giay/dang-nhap";  // Nếu người dùng chưa đăng nhập, chuyển đến trang đăng nhập
        }

        // Cập nhật đánh giá vào cơ sở dữ liệu
        reviewsService.updateReview(user.getId(), productId, comment);  // Cập nhật review nếu đã có

        return "redirect:/giay/san-pham"; // Sau khi sửa đánh giá, quay lại trang sản phẩm
    }
}

