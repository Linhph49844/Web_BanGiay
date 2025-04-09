package com.example.Wedsite_bangiay.repository;

import com.example.Wedsite_bangiay.model.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface GioHangRepository extends JpaRepository<GioHang, Long> {
    // Tìm giỏ hàng theo userId
    List<GioHang> findByUserId(Long userId);

    // Tìm giỏ hàng theo userId và productId
    Optional<GioHang> findByUserIdAndProductId(Long userId, Long productId);

}
