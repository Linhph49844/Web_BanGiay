package com.example.Wedsite_bangiay.repository;

import com.example.Wedsite_bangiay.model.AccKhachHang;
import com.example.Wedsite_bangiay.model.DonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonHangRepository extends JpaRepository<DonHang, Long> {

    // Phương thức để lấy tất cả các đơn hàng (đã có sẵn trong JpaRepository)
    List<DonHang> findAll();

    // Phương thức để tìm đơn hàng theo userId
    List<DonHang> findByAccKhachHang(AccKhachHang accKhachHang);
}
