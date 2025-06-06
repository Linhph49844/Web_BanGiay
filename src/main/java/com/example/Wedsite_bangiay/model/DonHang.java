package com.example.Wedsite_bangiay.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "donhang")
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private AccKhachHang accKhachHang;

    @Column (name = "total_amount")
    private BigDecimal totalAmount;

    private String status;

    @OneToMany(mappedBy = "donHang")
    private List<SanPham> sanPhamList;
}
