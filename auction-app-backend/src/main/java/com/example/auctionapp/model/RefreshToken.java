package com.example.auctionapp.model;

import com.example.auctionapp.entity.UserEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class RefreshToken {
    private UUID token_id;
    private String token;
    private LocalDateTime expiryDate;
    private UserEntity userEntity;

    public UUID getTokenId() {
        return token_id;
    }

    public void setTokenId(final UUID token_id) {
        this.token_id = token_id;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(final String token) {
        this.token = token;
    }

    public LocalDateTime getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(final LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public UserEntity getUserEntity() {
        return this.userEntity;
    }

    public void setUserEntity(final UserEntity userEntity) {
        this.userEntity = userEntity;
    }
}
