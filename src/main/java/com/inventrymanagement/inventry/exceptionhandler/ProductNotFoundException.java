package com.inventrymanagement.inventry.exceptionhandler;

public class ProductNotFoundException extends Throwable {
    public ProductNotFoundException(String s) {
        super(s);
    }
}
