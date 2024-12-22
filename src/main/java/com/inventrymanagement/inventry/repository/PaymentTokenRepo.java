package com.inventrymanagement.inventry.repository;

import com.inventrymanagement.inventry.entity.PaymentToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface PaymentTokenRepo extends JpaRepository<PaymentToken,Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM PaymentToken p WHERE p.createdAt > :expirationTime")
    void deleteByCreatedTimeGreaterThan(LocalDateTime expirationTime);

    PaymentToken findByEmail(String email);
}
