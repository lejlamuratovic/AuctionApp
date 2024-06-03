package com.example.auctionapp.controller;

import com.example.auctionapp.model.User;
import com.example.auctionapp.request.UserDetailsRequest;
import com.example.auctionapp.request.UserRequest;
import com.example.auctionapp.service.UserService;
import com.example.auctionapp.util.SecurityRoles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
