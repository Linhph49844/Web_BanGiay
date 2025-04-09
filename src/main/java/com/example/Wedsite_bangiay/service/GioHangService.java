package com.example.Wedsite_bangiay.service;

import com.example.Wedsite_bangiay.model.GioHang;
import com.example.Wedsite_bangiay.repository.GioHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GioHangService {

    @Autowired
    private GioHangRepository gioHangRepository;

    // Lấy giỏ hàng của người dùng
    public List<GioHang> getGioHangByUserId(Long userId) {
        return gioHangRepository.findByUserId(userId);  // Trả về giỏ hàng của người dùng
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public void xoaSanPhamFromGioHang(Long gioHangId) {
        gioHangRepository.deleteById(gioHangId);  // Xóa sản phẩm khỏi giỏ hàng theo id của giỏ hàng
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    public void capNhatSoLuong(Long gioHangId, int quantity) {
        Optional<GioHang> gioHangOptional = gioHangRepository.findById(gioHangId);
        if (gioHangOptional.isPresent()) {
            GioHang gioHang = gioHangOptional.get();
            gioHang.setQuantity(quantity);  // Cập nhật lại số lượng
            gioHangRepository.save(gioHang);  // Lưu lại giỏ hàng đã cập nhật
        }
    }

    // Tìm kiếm giỏ hàng theo userId và productId
    public Optional<GioHang> findByUserAndProduct(Long userId, Long productId) {
        return gioHangRepository.findByUserIdAndProductId(userId, productId);
    }

    // Lưu giỏ hàng
    public GioHang save(GioHang gioHang) {
        return gioHangRepository.save(gioHang);
    }
}
