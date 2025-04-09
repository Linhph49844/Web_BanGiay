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
@Table(name = "sanpham")  // Ánh xạ với bảng 'sanpham' trong cơ sở dữ liệu
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // Thay đổi tên cột từ 'idSanPham' thành 'id' để khớp với bảng 'sanpham'
    private Long id;  // Thay đổi tên trường thành 'id' thay vì 'idSanPham'

    @Column(name = "name")  // Cột 'name' trong bảng 'sanpham'
    private String name;

    @Column(name = "price")  // Cột 'price' trong bảng 'sanpham'
    private BigDecimal price;

    @Column(name = "quantity")  // Cột 'quantity' trong bảng 'sanpham'
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "id")  // 'category_id' tham chiếu đến 'id' trong bảng 'danhmuc'
    private DanhMuc danhMuc;  // Quan hệ với bảng DanhMuc
}
