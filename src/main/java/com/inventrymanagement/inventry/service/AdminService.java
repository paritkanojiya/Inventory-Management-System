package com.inventrymanagement.inventry.service;

import com.inventrymanagement.inventry.entity.Product;
import com.inventrymanagement.inventry.entity.Role;
import com.inventrymanagement.inventry.entity.User;
import jakarta.annotation.PostConstruct;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {

    private final PasswordEncoder passwordEncoder;
    private final AuthService userService;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final NotificationService notificationService;

    @PostConstruct
    public void init(){
        User user=new User();
        Role role=new Role();
        role.setUserRole("ROLE_ADMIN");
        user.setUserName("admin");
        user.setEmail("admin@gamil.com");
        user.setPassword(passwordEncoder.encode("admin123"));
        role.setUser(user);
        user.getRoles().add(role);
        User saveUser = userService.saveUser(user);
        UserDetails userDetails = userDetailsService.loadUserByUsername(saveUser.getEmail());
        String token = jwtService.createToken(userDetails);
        log.info("Admin created....");
        log.info("Admin authToken: {}",token);
    }


    public void QtyAlert(List<Product> productList) throws MessagingException {
        notificationService.sendAlert(productList);
    }
}
