package com.example.auctionapp.response;

public class NotificationResponse {
    private String message;

    public NotificationResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
