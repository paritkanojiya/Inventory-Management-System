package com.inventrymanagement.inventry.dto;

import com.inventrymanagement.inventry.entity.Product;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    List<Product> productList;
}
