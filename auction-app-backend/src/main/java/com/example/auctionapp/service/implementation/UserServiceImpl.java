package com.example.auctionapp.service.implementation;

import com.example.auctionapp.repository.UserRepository;

public class UserServiceImpl {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
