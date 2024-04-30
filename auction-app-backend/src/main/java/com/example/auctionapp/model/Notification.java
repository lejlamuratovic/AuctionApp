package com.example.auctionapp.model;

import java.time.LocalDateTime;
import java.util.UUID;

public class Notification {
    private UUID notificationId;
    private String messageContent;
    private LocalDateTime notificationTime;
    private UUID userId;
    private UUID productId;

    public Notification() { }

    public UUID getNotificationId() {
        return this.notificationId;
    }

    public void setNotificationId(final UUID notificationId) {
        this.notificationId = notificationId;
    }

    public String getMessageContent() {
        return this.messageContent;
    }

    public void setMessageContent(final String messageContent) {
        this.messageContent = messageContent;
    }

    public LocalDateTime getNotificationTime() {
        return this.notificationTime;
    }

    public void setNotificationTime(final LocalDateTime notificationTime) {
        this.notificationTime = notificationTime;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public void setUserId(final UUID userId) {
        this.userId = userId;
    }

    public UUID getProductId() {
        return this.productId;
    }

    public void setProductId(final UUID productId) {
        this.productId = productId;
    }
}
