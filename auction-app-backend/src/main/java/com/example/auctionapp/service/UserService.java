package com.example.auctionapp.service;

import com.example.auctionapp.model.User;
import com.example.auctionapp.request.UserDetailsRequest;

import java.util.UUID;

public interface UserService {
    User getUser(final UUID userId);

    User updateUser(final UUID userId, final UserDetailsRequest userRequest);
}
