package com.example.Wedsite_bangiay.service;

import com.example.Wedsite_bangiay.model.AccKhachHang;
import com.example.Wedsite_bangiay.repository.AccKhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccKhachHangService {

    @Autowired
    private AccKhachHangRepository accKhachHangRepository;

    // Lấy tất cả khách hàng
    public List<AccKhachHang> getAllKhachHang() {
        return accKhachHangRepository.findAll();
    }

    // Lấy khách hàng theo id
    public Optional<AccKhachHang> getKhachHangById(Long id) {
        return accKhachHangRepository.findById(id);
    }

    // Lưu hoặc cập nhật khách hàng
    public AccKhachHang saveKhachHang(AccKhachHang accKhachHang) {
        return accKhachHangRepository.save(accKhachHang);
    }

    // Tìm kiếm khách hàng theo tên, email hoặc địa chỉ
    public List<AccKhachHang> searchKhachHang(String query) {
        return accKhachHangRepository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrDiachiContainingIgnoreCase(query, query, query);
    }

    // Tìm khách hàng theo username và password (đăng nhập)
    public AccKhachHang findByUsernameAndPassword(String username, String password) {
        return accKhachHangRepository.findByUsernameAndPassword(username, password);
    }

    // Kiểm tra xem username đã tồn tại chưa (đăng ký)
    public boolean isUsernameExist(String username) {
        return accKhachHangRepository.findByUsernameAndPassword(username, null) != null;
    }

    // Thêm mới khách hàng (đăng ký)
    public AccKhachHang registerNewCustomer(AccKhachHang accKhachHang) {
        // Kiểm tra username đã tồn tại chưa
        if (isUsernameExist(accKhachHang.getUsername())) {
            return null;  // Trả về null nếu username đã tồn tại
        }
        // Nếu username chưa tồn tại, lưu khách hàng mới
        return accKhachHangRepository.save(accKhachHang);
    }
}
