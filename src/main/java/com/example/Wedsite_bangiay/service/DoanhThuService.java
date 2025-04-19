package com.example.Wedsite_bangiay.service;

import com.example.Wedsite_bangiay.model.DoanhThu;
import com.example.Wedsite_bangiay.repository.DoanhThuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoanhThuService {

    @Autowired
    private DoanhThuRepository doanhThuRepository;

    // Lấy danh sách tất cả doanh thu
    public List<DoanhThu> getAllDoanhThu() {
        return doanhThuRepository.findAll();
    }
}
