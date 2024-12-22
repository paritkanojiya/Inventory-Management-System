package com.inventrymanagement.inventry.service;

import com.inventrymanagement.inventry.entity.*;
import com.inventrymanagement.inventry.exceptionhandler.ProductNotFoundException;
import com.inventrymanagement.inventry.pojo.CalculateBillPrice;
import com.inventrymanagement.inventry.entity.Invoice;
import com.inventrymanagement.inventry.repository.InvoiceRepo;
import com.inventrymanagement.inventry.repository.UserRepo;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;
    private final ProductService productService;
    private final InvoiceRepo invoiceRepo;
    private final JwtService jwtService;
    private final AdminService adminService;



    public Invoice processOrderProduct(List<Product> productList,String token) throws MessagingException {
        User user = findCustomerByToken(token);
        List<Product> lessQtyProduct=new ArrayList<>();
        double totalPrice = productList.stream()
                .map(product -> {
                    try {
                        Product actualProduct = productService.findProductBYId(product.getPid());
                        productService.hasEnoughQty(actualProduct,product.getQty());
                        actualProduct.setQty(actualProduct.getQty()-product.getQty());
                        if(actualProduct.getQty()<=ProductService.PRODUCT_QTY_LIMIT){
                            lessQtyProduct.add(actualProduct);
                        }
                        product.setPrice(actualProduct.getPrice());
                        product.setDescription(actualProduct.getDescription());
                        product.setTitle(product.getTitle());
                        productService.saveProduct(actualProduct);
                        return product;
                    } catch (ProductNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                })
                .mapToDouble(product -> {
                    CalculateBillPrice calculateBillPrice = CalculateBillPrice.builder().mrp(product.getPrice() * product.getQty()).gstRate(18.0).build();
                    return calculateBillPrice.calculate();
                })
                .sum();
        if(!lessQtyProduct.isEmpty()){
            System.out.println("Notification send");
            adminService.QtyAlert(lessQtyProduct);
        }
        Invoice invoice = Invoice.builder()
                .gst(18.0)
                .totalProduct(productList.size())
                .totalPriceToPaid(totalPrice)
                .email(user.getEmail()).build();
        return invoiceRepo.save(invoice);
    }

    public User findCustomerByToken(String token){
        String userName = jwtService.getUserName(token.substring(7));
        return findUserByEmail(userName);
    }


    public User findUserByEmail(String email){
        return userRepo.findByEmail(email);
    }


}
