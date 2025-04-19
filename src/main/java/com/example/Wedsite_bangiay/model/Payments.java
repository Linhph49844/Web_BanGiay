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
@Table(name = "payments")
public class Payments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private DonHang donHang;  // Mối quan hệ với bảng DonHang

    @Column(name = "payment_method")
    private String paymentMethod;  // Phương thức thanh toán (ví dụ: "QR", "Cash", "Card")

    @Column(name = "payment_status", columnDefinition = "NVARCHAR(50) DEFAULT 'Chờ thanh toán'")
    private String paymentStatus;  // Trạng thái thanh toán (ví dụ: "Chờ thanh toán", "Đã thanh toán")

    @Column(name = "total_amount", nullable = false)
    private BigDecimal totalAmount;  // Tổng số tiền thanh toán

}
