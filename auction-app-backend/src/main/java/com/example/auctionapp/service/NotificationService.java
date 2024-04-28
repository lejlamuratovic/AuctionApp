package com.example.auctionapp.service;

import java.util.UUID;

public interface NotificationService {
    public void notifyUser(final UUID userId, final String message);
}
