package com.example.Wedsite_bangiay.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "doanhthu")
public class DoanhThu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  // ID của bản ghi doanh thu

    @Column(name = "date", nullable = false)
    private Date date;  // Ngày của doanh thu (Ngày tính doanh thu)

    @Column(name = "total_revenue", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalRevenue;  // Tổng doanh thu trong ngày

    // Khóa ngoại liên kết với bảng đơn hàng
    @ManyToOne
    @JoinColumn(name = "order_id")
    private DonHang donHang;

}
