package com.example.Wedsite_bangiay.service;

import com.example.Wedsite_bangiay.model.DanhMuc;
import com.example.Wedsite_bangiay.repository.DanhMucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DanhMucService {

    @Autowired
    private DanhMucRepository danhMucRepository;

    // Lấy tất cả danh mục
    public List<DanhMuc> getAllDanhMuc() {
        return danhMucRepository.findAll();
    }

    // Lấy danh mục theo ID
    public Optional<DanhMuc> getDanhMucById(Long id) {
        return danhMucRepository.findById(id);
    }

}
