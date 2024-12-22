package com.inventrymanagement.inventry.utils;

import com.inventrymanagement.inventry.repository.PaymentRepo;
import com.inventrymanagement.inventry.repository.PaymentTokenRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CleanUpTask {

    private final PaymentTokenRepo paymentTokenRepo;

    @Scheduled(fixedRate = 120000)
    public void cleanPaymentToken(){
        LocalDateTime expirationTime=LocalDateTime.now().minusMinutes(2);
        paymentTokenRepo.deleteByCreatedTimeGreaterThan(expirationTime);
    }
}
