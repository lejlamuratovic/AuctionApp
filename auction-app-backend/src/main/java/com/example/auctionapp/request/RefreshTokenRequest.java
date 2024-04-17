package com.example.auctionapp.request;

public class RefreshTokenRequest {
    private String token;

    public RefreshTokenRequest(final String token) {
        this.token = token;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(final String token) {
        this.token = token;
    }
}
