package com.example.Wedsite_bangiay.service;

import com.example.Wedsite_bangiay.model.SanPham;
import com.example.Wedsite_bangiay.repository.KhoHangRepository;
import com.example.Wedsite_bangiay.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KhoHangService {

    @Autowired
    private KhoHangRepository khoHangRepository;

    public List<SanPham> getAllSanPham() {
        return khoHangRepository.findAll();  // Phương thức này lấy tất cả sản phẩm từ database
    }
}