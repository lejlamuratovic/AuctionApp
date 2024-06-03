package com.example.auctionapp.controller;

import com.example.auctionapp.model.User;
import com.example.auctionapp.request.UserDetailsRequest;
import com.example.auctionapp.service.UserService;
import com.example.auctionapp.util.SecurityRoles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "JWT Security")
public class UserController {
    private final UserService userService;
    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize(SecurityRoles.ALL)
    @GetMapping("/{userId}")
    public User getUser(@PathVariable final UUID userId) {
        return userService.getUser(userId);
    }

    @PreAuthorize(SecurityRoles.ALL)
    @PutMapping("/{userId}")
    public User updateUser(@PathVariable final UUID userId, @RequestBody final UserDetailsRequest userRequest) {
        return userService.updateUser(userId, userRequest);
    }
        @PutMapping(path = "/{userId}/profile-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public User updateProfileImage(@PathVariable final UUID userId, @RequestPart("image") MultipartFile image) {
        return userService.updateProfileImage(userId, image);
    }
}
