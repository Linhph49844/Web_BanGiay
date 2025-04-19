package com.example.Wedsite_bangiay.service;

import com.example.Wedsite_bangiay.model.Reviews;
import com.example.Wedsite_bangiay.model.SanPham;
import com.example.Wedsite_bangiay.model.AccKhachHang;
import com.example.Wedsite_bangiay.repository.ReviewsRepository;
import com.example.Wedsite_bangiay.repository.SanPhamRepository;
import com.example.Wedsite_bangiay.repository.AccKhachHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewsService {

    @Autowired
    private ReviewsRepository reviewsRepository;

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @Autowired
    private AccKhachHangRepository accKhachHangRepository;

    // Lấy toàn bộ đánh giá
    public List<Reviews> getAllReviews() {
        return reviewsRepository.findAll();
    }

    // Lấy đánh giá theo ID sản phẩm
    public List<Reviews> getReviewsByProductId(Long productId) {
        return reviewsRepository.findBySanPham_Id(productId);
    }

    // Cập nhật đánh giá của người dùng cho sản phẩm
    public void updateReview(Long userId, Long productId, String comment) {
        // Tìm kiếm đánh giá theo người dùng và sản phẩm
        AccKhachHang user = accKhachHangRepository.findById(userId).orElse(null);
        SanPham sanPham = sanPhamRepository.findById(productId).orElse(null);

        if (user != null && sanPham != null) {
            Optional<Reviews> reviewOptional = reviewsRepository.findByAccKhachHangAndSanPham(user, sanPham);
            if (reviewOptional.isPresent()) {
                Reviews review = reviewOptional.get();
                review.setComment(comment);  // Cập nhật nội dung đánh giá
                reviewsRepository.save(review);  // Lưu đánh giá đã cập nhật
            }
        }
    }

    // Lấy đánh giá của người dùng cho sản phẩm
    public Reviews getReviewByUserAndProduct(Long userId, Long productId) {
        // Tìm kiếm người dùng và sản phẩm
        AccKhachHang user = accKhachHangRepository.findById(userId).orElse(null);
        SanPham sanPham = sanPhamRepository.findById(productId).orElse(null);

        if (user != null && sanPham != null) {
            return reviewsRepository.findByAccKhachHangAndSanPham(user, sanPham).orElse(null);
        }

        return null;
    }
}
