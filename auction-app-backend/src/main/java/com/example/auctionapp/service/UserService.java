package com.example.auctionapp.service;

import com.example.auctionapp.model.User;
import com.example.auctionapp.request.UserDetailsRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface UserService {
    User getUser(final UUID userId);

    User updateUser(final UUID userId, final UserDetailsRequest userRequest);

    User updateProfileImage(final UUID userId, final MultipartFile image);

    void deactivateAccount(final UUID userId);
}
