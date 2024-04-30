package com.example.auctionapp.service;

import com.example.auctionapp.model.Notification;

import java.util.UUID;

public interface NotificationService {
    void notifyUser(final UUID userId, final String message, final UUID productId);
    Notification getLatestNotificationForUserAndProduct(final UUID userId, final UUID productId);
}
