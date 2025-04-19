package com.example.Wedsite_bangiay.service;

import com.example.Wedsite_bangiay.model.AccKhachHang;
import com.example.Wedsite_bangiay.model.DonHang;
import com.example.Wedsite_bangiay.repository.DonHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DonHangService {

    @Autowired
    private DonHangRepository donHangRepository;

    @Autowired
    private AccKhachHangService accKhachHangService;

    // Lấy đơn hàng theo id
    public DonHang findById(Long id) {
        Optional<DonHang> donHang = donHangRepository.findById(id);
        return donHang.orElse(null);  // Trả về null nếu không tìm thấy đơn hàng
    }

    // Phương thức lấy đơn hàng theo ID
    public Optional<DonHang> getDonHangById(Long id) {
        return donHangRepository.findById(id);
    }

    // Lưu hoặc cập nhật đơn hàng
    public DonHang saveDonHang(DonHang donHang) {
        return donHangRepository.save(donHang);
    }
    public List<DonHang> getDonHangByUserId(Long userId) {
        // Lấy đối tượng người dùng từ AccKhachHangService
        Optional<AccKhachHang> optionalAccKhachHang = accKhachHangService.getKhachHangById(userId);

        // Kiểm tra xem Optional có chứa giá trị hay không
        if (optionalAccKhachHang.isPresent()) {
            AccKhachHang accKhachHang = optionalAccKhachHang.get();
            // Tìm các đơn hàng của người dùng
            return donHangRepository.findByAccKhachHang(accKhachHang);
        }

        return new ArrayList<>(); // Trả về danh sách trống nếu không tìm thấy người dùng
    }
}
