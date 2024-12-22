package com.inventrymanagement.inventry.controller;

import com.inventrymanagement.inventry.allenum.PaymentStatus;
import com.inventrymanagement.inventry.entity.Payment;
import com.inventrymanagement.inventry.entity.User;
import com.inventrymanagement.inventry.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {

    private final PaymentService paymentService;
    private final UserService userService;
    private final JwtService jwtService;
    private final NotificationService notificationService;
    private final ProductService productService;

    @GetMapping("/make-payment")
    public ResponseEntity<?> paymentApi(@RequestParam("token") String token, @RequestHeader("Authorization") String authToken) throws IOException {
        User user = userService.findCustomerByToken(authToken);
        boolean validatePaymentToken = paymentService.validatePaymentToken(user.getEmail(), token);
        if(validatePaymentToken){
            paymentService.deleteToken(user.getEmail());
            Payment payment = Payment.builder()
                    .status(PaymentStatus.SUCCESS)
                    .customerEmail(user.getEmail())
                    .customerName(user.getUserName())
                    .build();
            paymentService.savePayment(payment);
            return ResponseEntity.ok("You receive your product within 2 days");
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

}