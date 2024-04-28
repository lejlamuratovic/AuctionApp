package com.example.auctionapp.controller;

import com.example.auctionapp.request.NotificationRequest;
import com.example.auctionapp.service.NotificationService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(final NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/send-to/{userId}")
    public void notifyUser(final @PathVariable UUID userId,
                                final @RequestBody NotificationRequest notification) throws IOException {
        notificationService.notifyUser(userId, notification.getMessage());
    }
}