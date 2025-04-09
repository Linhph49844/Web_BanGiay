package com.example.Wedsite_bangiay.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "reviews")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Liên kết nhiều đánh giá với một khách hàng
    @ManyToOne
    @JoinColumn(name = "user_id")
    private AccKhachHang accKhachHang;

    // Liên kết nhiều đánh giá với một sản phẩm
    @ManyToOne
    @JoinColumn(name = "product_id")
    private SanPham sanPham;

    private String comment;

}