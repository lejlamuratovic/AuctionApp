package com.example.auctionapp.controller;

import com.example.auctionapp.exceptions.security.RefreshTokenNotFoundException;
import com.example.auctionapp.model.User;
import com.example.auctionapp.request.LoginRequest;
import com.example.auctionapp.request.UserRequest;
import com.example.auctionapp.response.JwtResponse;
import com.example.auctionapp.service.AuthService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
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

    @Value("${cookie.accessExpiry}")
    private int accessExpiry;

    @Value("${cookie.refreshExpiry}")
    private int refreshExpiry;

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

        // cookie for the access token
        ResponseCookie jwtCookie = ResponseCookie.from("accessToken", jwtResponse.getAccessToken())
                .httpOnly(true)
                .secure(jwtSecure)
                .path("/")
                .maxAge(accessExpiry)
                .build();

        // cookie for the refresh token
        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", jwtResponse.getRefreshToken())
                .httpOnly(true)
                .secure(jwtSecure)
                .path("/")
                .maxAge(refreshExpiry)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());

        return jwtResponse;
    }

    @PostMapping("/refresh-token")
    public String refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = null;
        final Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null) {
            throw new RefreshTokenNotFoundException("No refresh token found in request");
        }

        final String newAccessToken = authService.refreshAccessToken(refreshToken);

        // set a new cookie or update the existing one
        ResponseCookie jwtCookie = ResponseCookie.from("accessToken", newAccessToken)
                .httpOnly(true)
                .secure(jwtSecure) // true for production with HTTPS
                .path("/")
                .maxAge(refreshExpiry)
                .build();

        response.setHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());

        return newAccessToken;
    }

    @GetMapping("/logout")
    public void logout(HttpServletResponse response, HttpServletRequest request) {
        // delete refresh token from db
        String refreshToken = null;
        final Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }

        if (refreshToken == null) {
            throw new RefreshTokenNotFoundException("No refresh token found on logout");
        }

        authService.deleteRefreshToken(refreshToken);

        // expire tokens
        ResponseCookie accessTokenCookie = ResponseCookie.from("accessToken", null)
                .httpOnly(true)
                .secure(jwtSecure)
                .path("/")
                .maxAge(0) // invalidate
                .build();

        ResponseCookie refreshTokenCookie = ResponseCookie.from("refreshToken", null)
                .httpOnly(true)
                .secure(jwtSecure)
                .path("/")
                .maxAge(0) // invalidate
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
    }
}
