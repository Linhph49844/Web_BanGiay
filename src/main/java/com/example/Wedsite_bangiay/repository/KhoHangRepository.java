package com.example.Wedsite_bangiay.repository;

import com.example.Wedsite_bangiay.model.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface KhoHangRepository extends JpaRepository<SanPham, Long>{
//    List<SanPham> findAll();  // Tìm tất cả sản phẩm
    List<SanPham> findByNameContaining(String name);
}