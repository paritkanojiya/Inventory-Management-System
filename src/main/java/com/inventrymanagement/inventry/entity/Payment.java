package com.inventrymanagement.inventry.entity;


import com.inventrymanagement.inventry.allenum.PaymentStatus;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer paymentId;
    private Integer customerId;
    private String customerName;
    private String customerEmail;
    @Enumerated(EnumType.STRING)
    private PaymentStatus status=PaymentStatus.PENDING;
}