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
@Table(name = "acc_khachhang") // Bảng trong cơ sở dữ liệu
public class AccKhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;  // Tên tài khoản

    @Column(name = "password", nullable = false)
    private String password;  // Mật khẩu

    @Column(name = "name", nullable = false)
    private String name;  // Tên khách hàng

    @Column(name = "sdt")
    private String sdt;  // Số điện thoại

    @Column(name = "email")
    private String email;  // Email khách hàng

    @Column(name = "diachi")
    private String diachi;  // Địa chỉ khách hàng

}
