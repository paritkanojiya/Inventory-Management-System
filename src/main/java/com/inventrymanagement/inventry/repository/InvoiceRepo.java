package com.inventrymanagement.inventry.repository;

import com.inventrymanagement.inventry.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepo extends JpaRepository<Invoice
        ,Integer> {
}
