package com.example.Wedsite_bangiay.service;

import com.example.Wedsite_bangiay.model.AccNhanVien;
import com.example.Wedsite_bangiay.repository.AccNhanVienRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccNhanVienService {

    @Autowired
    private AccNhanVienRepository accNhanVienRepository;

    // Lấy tất cả nhân viên
    public List<AccNhanVien> getAllNhanVien() {
        return accNhanVienRepository.findAll();
    }

    // Lấy nhân viên theo id
    public Optional<AccNhanVien> getNhanVienById(Long id) {
        return accNhanVienRepository.findById(id);
    }

    // Lấy nhân viên theo username
    public AccNhanVien getNhanVienByUsername(String username) {
        return accNhanVienRepository.findByUsername(username);
    }

    // Lưu hoặc cập nhật nhân viên
    public AccNhanVien saveNhanVien(AccNhanVien accNhanVien) {
        return accNhanVienRepository.save(accNhanVien);
    }

    // Cập nhật thông tin nhân viên
    public AccNhanVien updateNhanVien(Long id, AccNhanVien accNhanVien) {
        Optional<AccNhanVien> existingNhanVien = accNhanVienRepository.findById(id);

        // Nếu nhân viên tồn tại, cập nhật thông tin
        if (existingNhanVien.isPresent()) {
            AccNhanVien updatedNhanVien = existingNhanVien.get();
            updatedNhanVien.setName(accNhanVien.getName());
            updatedNhanVien.setUsername(accNhanVien.getUsername());
            updatedNhanVien.setPassword(accNhanVien.getPassword());
            updatedNhanVien.setRole(accNhanVien.getRole());
            updatedNhanVien.setStatus(accNhanVien.getStatus());
            updatedNhanVien.setEmail(accNhanVien.getEmail());
            updatedNhanVien.setSdt(accNhanVien.getSdt());
            return accNhanVienRepository.save(updatedNhanVien);
        } else {
            return null;  // Nếu không tìm thấy nhân viên, trả về null
        }
    }

    public List<AccNhanVien> searchNhanVien(String query) {
        return accNhanVienRepository.findByNameContainingIgnoreCase(query);  // Tìm kiếm theo tên
    }

    // Tìm nhân viên theo tên đăng nhập và mật khẩu
    public AccNhanVien findByUsernameAndPassword(String username, String password) {
        return accNhanVienRepository.findByUsernameAndPassword(username, password);
    }
}
