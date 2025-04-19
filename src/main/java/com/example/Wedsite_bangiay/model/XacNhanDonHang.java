package com.example.Wedsite_bangiay.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "xacnhan_donhang")
public class XacNhanDonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "donhang_id")
    private DonHang donHang;  // Liên kết với bảng DonHang

    @Column(name = "confirmation_date")
    private Date confirmationDate;  // Ngày giờ xác nhận

    private String status;  // Trạng thái xác nhận (Đã xác nhận, Đang xử lý, Chờ xác nhận)
}
