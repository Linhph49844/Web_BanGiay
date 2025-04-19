package com.example.Wedsite_bangiay.repository;

import com.example.Wedsite_bangiay.model.LichSuMuaHang;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LichSuMuaHangRepository extends JpaRepository<LichSuMuaHang, Long> {
    List<LichSuMuaHang> findByAccKhachHangId(Long userId);  // Tìm lịch sử mua hàng theo userId
}
