package com.example.auctionapp.response;

public class LoginResponse {
    private String jwt;

    public LoginResponse(final String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return this.jwt;
    }

    public void setJwt(final String jwt) {
        this.jwt = jwt;
    }
}
