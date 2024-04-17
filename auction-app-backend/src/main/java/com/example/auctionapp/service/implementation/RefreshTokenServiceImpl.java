package com.example.auctionapp.service.implementation;

import com.example.auctionapp.entity.RefreshTokenEntity;
import com.example.auctionapp.entity.UserEntity;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.exceptions.security.ExpiredTokenException;
import com.example.auctionapp.model.RefreshToken;
import com.example.auctionapp.repository.RefreshTokenRepository;
import com.example.auctionapp.repository.UserRepository;
import com.example.auctionapp.service.RefreshTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenServiceImpl(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RefreshToken createRefreshToken(final String username) {
        final Optional<UserEntity> user = userRepository.findUserEntityByEmail(username);

        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User does not exist.");
        }

        // retrieve existing refresh tokens for the user
        List<RefreshTokenEntity> existingTokens = refreshTokenRepository.findByUserEntity(user.get());

        // delete existing tokens to ensure only new one is existing
        if (!existingTokens.isEmpty()) {
            refreshTokenRepository.deleteAll(existingTokens);
        }

        // generate a new token
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        refreshToken.setUserEntity(user.get());
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(LocalDateTime.now().plusMinutes(10)); // 10 minutes expiry time

        return refreshTokenRepository.save(refreshToken).toDomainModel();
    }

    @Override
    public Optional<RefreshToken> findByToken(final String token) {
        Optional<RefreshTokenEntity> refreshTokenEntity = refreshTokenRepository.findRefreshTokenEntityByToken(token);

        return refreshTokenEntity.map(RefreshTokenEntity::toDomainModel);
    }

    @Override
    public RefreshTokenEntity verifyExpiration(final RefreshTokenEntity token) {
        LocalDateTime now = LocalDateTime.now();

        if(token.getExpiryDate().isBefore(now)){
            refreshTokenRepository.delete(token);
            throw new ExpiredTokenException(token.getToken() + "Refresh token is expired.");
        }

        return token;
    }
}
