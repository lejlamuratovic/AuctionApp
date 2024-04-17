package com.example.auctionapp.service;

import com.example.auctionapp.entity.RefreshTokenEntity;
import com.example.auctionapp.model.RefreshToken;

import java.util.Optional;

public interface RefreshTokenService {
    RefreshToken verifyExpiration(final RefreshTokenEntity token);
    Optional<RefreshToken> findByToken(final String token);
    RefreshToken createRefreshToken(final String username);
}
