package com.example.Wedsite_bangiay.service;

import com.example.Wedsite_bangiay.model.DonHang;
import com.example.Wedsite_bangiay.model.XacNhanDonHang;
import com.example.Wedsite_bangiay.repository.XacNhanDonHangRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class XacNhanDonHangService {

    private final XacNhanDonHangRepository xacNhanDonHangRepository;

    @Autowired
    public XacNhanDonHangService(XacNhanDonHangRepository xacNhanDonHangRepository) {
        this.xacNhanDonHangRepository = xacNhanDonHangRepository;
    }

    // Lấy tất cả các đơn hàng cần xác nhận
    public List<XacNhanDonHang> getAllXacNhanDonHang() {
        return xacNhanDonHangRepository.findAll();
    }

    // Xác nhận đơn hàng
    public XacNhanDonHang confirmOrder(Long id) {
        Optional<XacNhanDonHang> optionalOrder = xacNhanDonHangRepository.findById(id);
        if (optionalOrder.isPresent()) {
            XacNhanDonHang xacNhanDonHang = optionalOrder.get();
            xacNhanDonHang.setStatus("Đã xác nhận");
            xacNhanDonHang.setConfirmationDate(new Date());
            return xacNhanDonHangRepository.save(xacNhanDonHang);
        }
        return null;
    }

    // Hủy xác nhận đơn hàng
    public XacNhanDonHang cancelOrder(Long id) {
        Optional<XacNhanDonHang> optionalOrder = xacNhanDonHangRepository.findById(id);
        if (optionalOrder.isPresent()) {
            XacNhanDonHang xacNhanDonHang = optionalOrder.get();
            xacNhanDonHang.setStatus("Chờ xác nhận");
            return xacNhanDonHangRepository.save(xacNhanDonHang);
        }
        return null;
    }

    public XacNhanDonHang saveXacNhan(XacNhanDonHang xacNhanDonHang) {
        return xacNhanDonHangRepository.save(xacNhanDonHang);
    }

    public XacNhanDonHang createXacNhanDonHang(DonHang donHang) {
        XacNhanDonHang xacNhanDonHang = new XacNhanDonHang();
        xacNhanDonHang.setDonHang(donHang);
        xacNhanDonHang.setStatus("Chờ xác nhận"); // Trạng thái mặc định
        return xacNhanDonHangRepository.save(xacNhanDonHang);  // Lưu vào DB
    }
}
