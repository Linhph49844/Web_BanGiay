package com.example.Wedsite_bangiay.repository;

import com.example.Wedsite_bangiay.model.AccKhachHang;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccKhachHangRepository extends JpaRepository<AccKhachHang, Long> {

    // Tìm kiếm khách hàng theo tên, email hoặc địa chỉ
    List<AccKhachHang> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrDiachiContainingIgnoreCase(String name, String email, String diachi);

    // Tìm khách hàng theo username và password
    AccKhachHang findByUsernameAndPassword(String username, String password);

    // Tìm khách hàng theo username
    AccKhachHang findByUsername(String username);
}
