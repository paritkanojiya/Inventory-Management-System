package com.inventrymanagement.inventry.repository;

import com.inventrymanagement.inventry.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product,Integer> {
}
