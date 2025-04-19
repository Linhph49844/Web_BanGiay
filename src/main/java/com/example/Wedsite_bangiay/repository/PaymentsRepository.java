package com.example.Wedsite_bangiay.repository;

import com.example.Wedsite_bangiay.model.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<Payments, Long> {
}
