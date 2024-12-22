package com.inventrymanagement.inventry.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;

@Service
@Slf4j
public class JwtService {
    @Value("${jwt.service.secret-key}")
    private String SECRET_KEY;
    @Value("${jwt.service.expiration}")
    private Integer EXPIRATION_TIME;

    public String createToken(UserDetails userDetails){
        return Jwts.builder().subject(userDetails.getUsername()).signWith(key()).expiration(new Date(System.currentTimeMillis()+EXPIRATION_TIME)).compact();
    }
    public String getUserName(String token){
        return Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token).getPayload().getSubject();
    }
    public boolean validateToken(String token) throws JwtException, IllegalArgumentException{
        try{
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(token);
            return true;
        }
        catch (Exception e){
            log.error("token invalid ",e);
        }
        return false;
    }
    public Key key(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }
}
