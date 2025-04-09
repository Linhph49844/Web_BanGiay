package com.example.Wedsite_bangiay.service;

import com.example.Wedsite_bangiay.model.Reviews;
import com.example.Wedsite_bangiay.repository.ReviewsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewsService {

    @Autowired
    private ReviewsRepository reviewsRepository;

    // Lấy toàn bộ đánh giá
    public List<Reviews> getAllReviews() {
        return reviewsRepository.findAll();
    }

    // Lấy đánh giá theo ID sản phẩm
    public List<Reviews> getReviewsByProductId(Long productId) {
        return reviewsRepository.findBySanPham_Id(productId);
    }

}
