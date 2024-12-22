package com.inventrymanagement.inventry.repository;

import com.inventrymanagement.inventry.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment,Integer> {
}
