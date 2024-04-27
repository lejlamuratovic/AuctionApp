package com.example.auctionapp.request;

public class NotificationRequest {
    private String message;

    public NotificationRequest() {}

    public NotificationRequest(final String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
