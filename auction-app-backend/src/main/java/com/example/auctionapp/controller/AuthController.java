package com.example.auctionapp.controller;

import com.example.auctionapp.model.User;
import com.example.auctionapp.request.LoginRequest;
import com.example.auctionapp.request.RefreshTokenRequest;
import com.example.auctionapp.request.UserRequest;
import com.example.auctionapp.response.JwtResponse;
import com.example.auctionapp.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/auth")
@SecurityRequirement(name = "JWT Security")
public class AuthController {
    private final AuthService authService;

    @Value("${JWT_SECURE}")
    private boolean jwtSecure;

    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public User register(@RequestBody final UserRequest user) {
        return authService.signUp(user);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody final LoginRequest loginRequest, HttpServletResponse response) {
        JwtResponse jwtResponse = authService.signIn(loginRequest);

        ResponseCookie jwtCookie = ResponseCookie.from("accessToken", jwtResponse.getAccessToken())
                .httpOnly(true)
                .secure(false) // true for production with HTTPS
                .path("/")
                .maxAge(24 * 60 * 60) // valid for 1 day
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        return jwtResponse;
    }

    @PostMapping("/refresh-token")
    public String refreshToken(@RequestParam final RefreshTokenRequest refreshToken, HttpServletResponse response) {
        String newAccessToken = authService.refreshAccessToken(refreshToken);

        // set a new cookie or update the existing one
        ResponseCookie jwtCookie = ResponseCookie.from("accessToken", newAccessToken)
                .httpOnly(true)
                .secure(jwtSecure) // true for production with HTTPS
                .path("/")
                .maxAge(7 * 24 * 60 * 60) // valid for 1 week
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        return newAccessToken;
    }

    @GetMapping("/logout")
    public void logout(HttpServletResponse response) {
        // expire the accessToken cookie
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", null)
                .httpOnly(true)
                .secure(jwtSecure)
                .path("/")
                .maxAge(0) // invalidate
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
    }
}

