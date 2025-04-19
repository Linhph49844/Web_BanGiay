package com.example.Wedsite_bangiay.repository;

import com.example.Wedsite_bangiay.model.AccKhachHang;
import com.example.Wedsite_bangiay.model.Reviews;
import com.example.Wedsite_bangiay.model.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Long> {  // Sử dụng Long thay vì Integer cho ID
    List<Reviews> findBySanPham_Id(Long productId);

    // Tìm đánh giá của người dùng cho sản phẩm
    Optional<Reviews> findByAccKhachHangAndSanPham(AccKhachHang accKhachHang, SanPham sanPham);
}
