package com.example.Wedsite_bangiay.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;

import javax.imageio.ImageIO;

@Service
public class QRCodeService {

    public String generateQRCode(String paymentInfo) throws Exception {
        int width = 300;
        int height = 300;
        String filePath = "/path/to/save/qrcode.png"; // Đường dẫn lưu mã QR

        // Tạo mã QR từ thông tin thanh toán
        MultiFormatWriter barcodeWriter = new MultiFormatWriter();
        BitMatrix bitMatrix = barcodeWriter.encode(paymentInfo, BarcodeFormat.QR_CODE, width, height);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, (bitMatrix.get(x, y) ? 0 : 0xFFFFFF));
            }
        }

        // Lưu mã QR vào file
        ImageIO.write(image, "PNG", new File(filePath));

        return filePath; // Trả về đường dẫn đến mã QR đã được tạo
    }
}