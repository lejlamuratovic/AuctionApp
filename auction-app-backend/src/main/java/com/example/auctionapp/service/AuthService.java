package com.example.auctionapp.service;

import com.example.auctionapp.model.User;
import com.example.auctionapp.request.LoginRequest;
import com.example.auctionapp.request.UserRequest;
import com.example.auctionapp.response.LoginResponse;

public interface AuthService {
    User signUp(UserRequest userRequest);
    LoginResponse signIn(LoginRequest loginRequest);
}
