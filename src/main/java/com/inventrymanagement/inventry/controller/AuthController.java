package com.inventrymanagement.inventry.controller;

import com.inventrymanagement.inventry.entity.User;
import com.inventrymanagement.inventry.exceptionhandler.UserAlreadyExits;
import com.inventrymanagement.inventry.response.AuthRequest;
import com.inventrymanagement.inventry.response.AuthResponse;
import com.inventrymanagement.inventry.service.JwtService;
import com.inventrymanagement.inventry.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> registerUser(@RequestBody User user) throws UserAlreadyExits {
        try{
            userService.isUserExist(user);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User saveduser = userService.saveUser(user);
            UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
            String token = jwtService.createToken(userDetails);
            AuthResponse response = AuthResponse.builder().user(saveduser).token(token).build();
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        }
        catch (UserAlreadyExits ex){
            throw new UserAlreadyExits(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody AuthRequest authRequest){
        Authentication authenticated=null;
        try{
            authenticated = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(),authRequest.getPassword()));
        }
        catch (Exception e){
//            log.error("",e);
        }
        SecurityContextHolder.getContext().setAuthentication(authenticated);
        UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());
        String token = jwtService.createToken(userDetails);
        return ResponseEntity.ok(token);
    }
}
