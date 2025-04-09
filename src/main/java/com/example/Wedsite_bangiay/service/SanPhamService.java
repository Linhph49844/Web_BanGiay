package com.example.Wedsite_bangiay.service;

import com.example.Wedsite_bangiay.model.SanPham;
import com.example.Wedsite_bangiay.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SanPhamService {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    // Lấy tất cả sản phẩm
    public List<SanPham> getAllSanPham() {
        return sanPhamRepository.findAll();
    }

    // Lấy sản phẩm theo ID
    public Optional<SanPham> getSanPhamById(Long id) {
        return sanPhamRepository.findById(id);
    }

    // Lấy sản phẩm theo danh mục
    public List<SanPham> getSanPhamByDanhMuc(Long danhMucId) {
        return sanPhamRepository.findByDanhMucId(danhMucId); // Truy vấn sản phẩm theo danh mục
    }

    // Tìm sản phẩm theo tên
    public List<SanPham> searchSanPham(String query) {
        return sanPhamRepository.findByNameContainingIgnoreCase(query); // Tìm sản phẩm theo tên
    }

    public SanPham addSanPham(SanPham sanPham) {
        return sanPhamRepository.save(sanPham); // Lưu sản phẩm vào cơ sở dữ liệu
    }

    // Sửa sản phẩm
    public SanPham updateSanPham(Long id, SanPham sanPham) {
        if (sanPhamRepository.existsById(id)) {
            sanPham.setId(id); // Cập nhật lại ID của sản phẩm
            return sanPhamRepository.save(sanPham); // Lưu sản phẩm đã sửa
        }
        return null; // Trả về null nếu không tìm thấy sản phẩm
    }

    // Xóa sản phẩm
    public boolean deleteSanPham(Long id) {
        if (sanPhamRepository.existsById(id)) {
            sanPhamRepository.deleteById(id); // Xóa sản phẩm theo ID
            return true;
        }
        return false; // Trả về false nếu không tìm thấy sản phẩm để xóa
    }

    // Cập nhật số lượng sản phẩm
    public SanPham updateProductQuantity(Long id, int newQuantity) {
        Optional<SanPham> sanPhamOpt = sanPhamRepository.findById(id);
        if (sanPhamOpt.isPresent()) {
            SanPham sanPham = sanPhamOpt.get();
            sanPham.setQuantity(newQuantity);  // Cập nhật số lượng mới
            return sanPhamRepository.save(sanPham);  // Lưu lại sản phẩm với số lượng mới
        }
        return null; // Trả về null nếu không tìm thấy sản phẩm
    }

    // Kiểm tra tồn kho sản phẩm
    public boolean isProductInStock(Long id) {
        Optional<SanPham> sanPhamOpt = sanPhamRepository.findById(id);
        return sanPhamOpt.isPresent() && sanPhamOpt.get().getQuantity() > 0; // Kiểm tra số lượng
    }

    // Lấy sản phẩm theo tên và danh mục
    public List<SanPham> getProductsByNameAndCategory(String name, Long danhMucId) {
        return sanPhamRepository.findAll().stream()
                .filter(sanPham -> sanPham.getName().toLowerCase().contains(name.toLowerCase())
                        && sanPham.getDanhMuc().getId().equals(danhMucId))
                .toList();
    }
}
