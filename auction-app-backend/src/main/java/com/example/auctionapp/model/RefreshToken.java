package com.example.auctionapp.model;

import com.example.auctionapp.entity.RefreshTokenEntity;
import com.example.auctionapp.entity.UserEntity;
import com.example.auctionapp.util.builderpattern.GenericBuilder;

import java.time.LocalDateTime;
import java.util.UUID;

public class RefreshToken {
    private UUID tokenId;
    private String token;
    private LocalDateTime expiryDate;
    private UserEntity userEntity;

    public RefreshTokenEntity toEntity() {
        return GenericBuilder.of(RefreshTokenEntity::new)
                .with(RefreshTokenEntity::setTokenId, this.tokenId)
                .with(RefreshTokenEntity::setToken, this.token)
                .with(RefreshTokenEntity::setExpiryDate, this.expiryDate)
                .with(RefreshTokenEntity::setUserEntity, this.userEntity)
                .build();
    }

    public UUID getTokenId() {
        return this.tokenId;
    }

    public void setTokenId(final UUID tokenId) {
        this.tokenId = tokenId;
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
