package com.inventrymanagement.inventry.controller;

import com.inventrymanagement.inventry.dto.OrderDto;
import com.inventrymanagement.inventry.entity.Invoice;
import com.inventrymanagement.inventry.service.PaymentService;
import com.inventrymanagement.inventry.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final PaymentService paymentService;

    @PostMapping("/place-order")
    public ResponseEntity<Invoice> purchaseProduct(HttpServletResponse response, @RequestBody OrderDto orderDto, @RequestHeader("Authorization") String token) throws Exception {
        Invoice invoice = userService.processOrderProduct(orderDto.getProductList(), token);
        String generatedPaymentToken = paymentService.generatePaymentToken(invoice.getEmail());
        response.addHeader("Location","http://localhost:9000/payment/make-payment?token="+generatedPaymentToken);
        return ResponseEntity.ok(invoice);
    }
}