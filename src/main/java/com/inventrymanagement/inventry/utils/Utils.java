package com.inventrymanagement.inventry.utils;

import com.inventrymanagement.inventry.entity.Product;
import com.inventrymanagement.inventry.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.Executor;

@Service
public class Utils {

    @Autowired
    @Qualifier("executor")
    private  Executor executor;
    @Autowired
    private ProductRepo productRepo;
    public void bulkSaveProductTask(List<Product> productList){
        int chunk=100;
        for(int i=0;i<productList.size();i=i+chunk) {
            List<Product> products = productList.subList(i, Math.min(chunk + i, productList.size()));
            executor.execute(()->productRepo.saveAll(products));
        }
    }
}
