package com.inventrymanagement.inventry.exceptionhandler;

public class UserAlreadyExits extends Exception{
    public UserAlreadyExits(String message) {
        super(message);
    }
}
