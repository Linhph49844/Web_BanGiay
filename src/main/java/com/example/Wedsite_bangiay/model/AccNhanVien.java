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
@Table(name = "acc_nhanvien") // Bảng trong cơ sở dữ liệu
public class AccNhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false)
    private String username;  // Tên tài khoản

    @Column(name = "password", nullable = false)
    private String password;  // Mật khẩu

    @Column(name = "name", nullable = false)
    private String name;  // Tên nhân viên

    @Column(name = "sdt")
    private String sdt;  // Số điện thoại

    @Column(name = "email")
    private String email;  // Email nhân viên

    @Column(name = "status", nullable = false)
    private String status = "Đang làm";  // Trạng thái: "Đang làm", "Đã nghỉ"

    @ManyToOne(fetch = FetchType.LAZY)  // Quan hệ với bảng roles
    @JoinColumn(name = "role_id", referencedColumnName = "id", nullable = false)
    private Role role;  // Liên kết với bảng roles

}
