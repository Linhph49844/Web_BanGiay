package com.example.Wedsite_bangiay.repository;

import com.example.Wedsite_bangiay.model.AccNhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccNhanVienRepository extends JpaRepository<AccNhanVien, Long> {
    // Các phương thức tùy chỉnh (nếu cần)
    // Ví dụ: tìm kiếm nhân viên theo username
    AccNhanVien findByUsername(String username);

    // Phương thức tìm nhân viên theo tên, không phân biệt chữ hoa chữ thường
    List<AccNhanVien> findByNameContainingIgnoreCase(String name);

    // Tìm nhân viên theo tên đăng nhập và mật khẩu
    AccNhanVien findByUsernameAndPassword(String username, String password);
}
