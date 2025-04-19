package com.example.Wedsite_bangiay.repository;

import com.example.Wedsite_bangiay.model.XacNhanDonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface XacNhanDonHangRepository extends JpaRepository<XacNhanDonHang, Long> {
    // You can add custom queries here if needed
}
