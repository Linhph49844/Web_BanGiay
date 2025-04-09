package com.example.Wedsite_bangiay.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "danhmuc")  // Ánh xạ với bảng 'danhmuc' trong cơ sở dữ liệu
public class DanhMuc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // Thay đổi 'iddanhMuc' thành 'id' để khớp với cơ sở dữ liệu
    private Long id;  // Thay đổi tên trường thành 'idDanhMuc' thay vì 'iddanhMuc'

    @Column(name = "name")
    private String name;

    // Quan hệ một-nhiều với bảng SanPham
    @OneToMany(mappedBy = "danhMuc", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SanPham> listSanPham = new ArrayList<>();
}
