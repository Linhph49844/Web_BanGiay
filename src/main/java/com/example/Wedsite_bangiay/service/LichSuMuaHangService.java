package com.example.Wedsite_bangiay.service;

import com.example.Wedsite_bangiay.model.LichSuMuaHang;
import com.example.Wedsite_bangiay.repository.LichSuMuaHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LichSuMuaHangService {

    @Autowired
    private LichSuMuaHangRepository lichSuMuaHangRepository;

    // Lấy lịch sử mua hàng của người dùng
    public List<LichSuMuaHang> getLichSuMuaHangByUserId(Long userId) {
        return lichSuMuaHangRepository.findByAccKhachHangId(userId);
    }
}
