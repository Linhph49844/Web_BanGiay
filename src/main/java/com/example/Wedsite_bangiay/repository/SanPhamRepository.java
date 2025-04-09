package com.example.Wedsite_bangiay.repository;

import com.example.Wedsite_bangiay.model.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, Long> {
    // Tìm sản phẩm theo danh mục
    List<SanPham> findByDanhMucId(Long danhMucId);
    
    // Tìm sản phẩm theo tên
    List<SanPham> findByNameContainingIgnoreCase(String name);
}
