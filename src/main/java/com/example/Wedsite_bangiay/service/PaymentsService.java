package com.example.Wedsite_bangiay.service;

import com.example.Wedsite_bangiay.model.Payments;
import com.example.Wedsite_bangiay.repository.PaymentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentsService {

    @Autowired
    private PaymentsRepository paymentsRepository;

    public Payments savePayments(Payments payments) {
        return paymentsRepository.save(payments);
    }
}
