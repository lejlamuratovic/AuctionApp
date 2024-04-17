package com.example.auctionapp.controller;

import com.example.auctionapp.model.User;
import com.example.auctionapp.request.LoginRequest;
import com.example.auctionapp.request.RefreshTokenRequest;
import com.example.auctionapp.request.UserRequest;
import com.example.auctionapp.response.JwtResponse;
import com.example.auctionapp.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public User register(@RequestBody final UserRequest user) {
        return authService.signUp(user);
    }

    @PostMapping("/login")
    public JwtResponse login(@RequestBody final LoginRequest loginRequest) {
        return authService.signIn(loginRequest);
    }

    @PostMapping("/refresh-token")
    public String refreshToken(@RequestParam final RefreshTokenRequest refreshToken) {
        return authService.refreshAccessToken(refreshToken);
    }
}
