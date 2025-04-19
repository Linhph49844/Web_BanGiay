package com.example.Wedsite_bangiay.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "lichsu_mua_hang")
public class LichSuMuaHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AccKhachHang accKhachHang;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private SanPham sanPham;

    private int quantity;

    private BigDecimal total_amount;
}
