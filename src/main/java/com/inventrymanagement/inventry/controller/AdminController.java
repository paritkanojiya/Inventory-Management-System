package com.inventrymanagement.inventry.controller;

import com.inventrymanagement.inventry.entity.Product;
import com.inventrymanagement.inventry.exceptionhandler.ProductNotFoundException;
import com.inventrymanagement.inventry.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ProductService productService;

    @PostMapping("/add-product")
    public ResponseEntity<String> addProduct(@RequestBody List<Product> productList){
        productService.saveProducts(productList);
        return ResponseEntity.status(HttpStatus.CREATED).body("product save successfully");
    }

    @PostMapping("/update-product")
    @ResponseStatus(HttpStatus.CREATED)
    public void updateProduct(@RequestBody Product product) throws ProductNotFoundException {
        productService.updateProduct(product);
    }

    @DeleteMapping("/delete-product/{id}")
    public void deleteProduct(@PathVariable("id") Integer id){
        productService.deleteProductById(id);
    }

    @GetMapping("/get-product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Integer id) throws ProductNotFoundException {
        Product product = productService.findProductBYId(id);
        return ResponseEntity.ok(product);
    }
}
