package com.inventrymanagement.inventry.filter;

import com.inventrymanagement.inventry.service.JwtService;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain) throws ServletException, IOException {
        String header=extractToken(request);
        if(header!=null && header.startsWith("Bearer") && SecurityContextHolder.getContext().getAuthentication()==null) {
            String token = header.substring(7);
            try {
                jwtService.validateToken(token);
                String userName = jwtService.getUserName(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
                Authentication authenticated = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticated);
            } catch (Exception e) {
//                log.error("token : ", e);
            }
        }
        filterChain.doFilter(request,response);
    }
    private String extractToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }
}
