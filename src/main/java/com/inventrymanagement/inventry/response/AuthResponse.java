package com.inventrymanagement.inventry.response;

import com.inventrymanagement.inventry.entity.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private User user;
    private String token;
}
