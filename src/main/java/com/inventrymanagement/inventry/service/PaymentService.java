package com.inventrymanagement.inventry.service;

import com.inventrymanagement.inventry.entity.Payment;
import com.inventrymanagement.inventry.entity.PaymentToken;
import com.inventrymanagement.inventry.repository.PaymentRepo;
import com.inventrymanagement.inventry.repository.PaymentTokenRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepo paymentRepo;
    private final PaymentTokenRepo paymentTokenRepo;

    public String generatePaymentToken(String email) throws Exception {
        PaymentToken paymentToken=paymentTokenRepo.findByEmail(email);
        if(paymentToken!=null) throw new Exception("Try again after 2 minutes");
        String token = UUID.randomUUID().toString();
        PaymentToken paymentTokenObj = PaymentToken.builder()
                .token(token)
                .email(email).build();
        paymentTokenRepo.save(paymentTokenObj);
        return token;
    }

    public boolean validatePaymentToken(String email,String token){
        PaymentToken paymentToken=paymentTokenRepo.findByEmail(email);
        return paymentToken.getToken().equals(token);
    }

    public void deleteToken(String email){
        PaymentToken paymentToken=paymentTokenRepo.findByEmail(email);
        paymentTokenRepo.delete(paymentToken);
    }

    public void savePayment(Payment payment){
        paymentRepo.save(payment);
    }
}