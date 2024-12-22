package com.inventrymanagement.inventry.response;

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String password;
}
