package com.inventrymanagement.inventry.service;

import com.inventrymanagement.inventry.entity.Product;
import com.inventrymanagement.inventry.exceptionhandler.ProductNotFoundException;
import com.inventrymanagement.inventry.repository.ProductRepo;
import com.inventrymanagement.inventry.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepo productRepo;
    public static final int PRODUCT_QTY_LIMIT=5;
    private final Utils utils;

    public void saveProducts(List<Product> productList) {
        utils.bulkSaveProductTask(productList);
    }

    public Product findProductBYId(Integer pid) throws ProductNotFoundException {
        Optional<Product> product = productRepo.findById(pid);
        if(product.isPresent())
            return product.get();
        throw new ProductNotFoundException("provided id product is not found");
    }

    public void saveProduct(Product product){
        productRepo.save(product);
    }

    public void hasEnoughQty(Product product,Integer qty){
        if(product.getQty()<qty){
            throw new RuntimeException(product.getTitle()+" not have enough qty");
        }
    }

    public void deleteProductById(Integer id){
        productRepo.deleteById(id);
    }
    
    public void updateProduct(Product updateProductRequest) throws ProductNotFoundException {
        productRepo.save(updateProductRequest);
    }
}
