package com.example.Wedsite_bangiay.repository;

import com.example.Wedsite_bangiay.model.DoanhThu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoanhThuRepository extends JpaRepository<DoanhThu, Long> {
    // Lấy danh sách tất cả doanh thu
    List<DoanhThu> findAll();
}
