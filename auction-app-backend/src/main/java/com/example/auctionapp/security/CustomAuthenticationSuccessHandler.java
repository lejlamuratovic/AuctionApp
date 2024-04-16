package com.example.auctionapp.security;

import com.example.auctionapp.service.implementation.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private JwtService jwtService;

    @Value("${security.jwt.cookieExpiry}")
    private int cookieExpiry;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String token = jwtService.generateToken(userDetails);

        // create and set the JWT as an http only cookie
        ResponseCookie jwtCookie = ResponseCookie.from("accessToken", token)
                .httpOnly(true)
                .secure(false) // true for production with https
                .path("/")
                .maxAge(cookieExpiry)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
    }
}
