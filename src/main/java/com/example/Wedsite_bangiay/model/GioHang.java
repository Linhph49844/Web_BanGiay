package com.example.Wedsite_bangiay.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "giohang")
public class GioHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ID của giỏ hàng

    @ManyToOne(fetch = FetchType.LAZY)  // Liên kết với bảng `acc_khachhang`
    @JoinColumn(name = "user_id", nullable = false)
    private AccKhachHang user;  // Khách hàng sở hữu giỏ hàng

    @ManyToOne(fetch = FetchType.LAZY)  // Liên kết với bảng `sanpham`
    @JoinColumn(name = "product_id", nullable = false)
    private SanPham product;  // Sản phẩm trong giỏ hàng
    @Column(name = "quantity", nullable = false)
    private int quantity;  // Số lượng sản phẩm trong giỏ hàng
}
