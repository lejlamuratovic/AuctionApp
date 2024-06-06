package com.example.auctionapp.service.implementation;

import com.example.auctionapp.entity.RefreshTokenEntity;
import com.example.auctionapp.entity.UserEntity;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.exceptions.security.ExpiredTokenException;
import com.example.auctionapp.model.RefreshToken;
import com.example.auctionapp.repository.RefreshTokenRepository;
import com.example.auctionapp.repository.UserRepository;
import com.example.auctionapp.service.RefreshTokenService;
import com.example.auctionapp.util.HashRefreshToken;
import com.example.auctionapp.util.builderpattern.GenericBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshTokenServiceImpl(final RefreshTokenRepository refreshTokenRepository, final UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    @Override
    public RefreshToken createRefreshToken(final String username) {
        final UserEntity userEntity = userRepository.findUserEntityByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User does not exist."));

        // if no valid token found create a new one and save to db
        final String rawToken = UUID.randomUUID().toString();
        final String hashedToken = HashRefreshToken.hashToken(rawToken);

        final RefreshTokenEntity refreshToken = GenericBuilder.of(RefreshTokenEntity::new)
                        .with(RefreshTokenEntity::setUserEntity, userEntity)
                        .with(RefreshTokenEntity::setToken, hashedToken)
                        .with(RefreshTokenEntity::setExpiryDate, LocalDateTime.now().plusDays(7))
                        .build();

        refreshTokenRepository.save(refreshToken);

        // domain model with raw token
        return GenericBuilder.of(RefreshToken::new)
                .with(RefreshToken::setToken, rawToken)
                .with(RefreshToken::setTokenId, refreshToken.getTokenId())
                .with(RefreshToken::setExpiryDate, refreshToken.getExpiryDate())
                .with(RefreshToken::setUserEntity, userEntity)
                .build();
    }

    @Override
    public Optional<RefreshToken> findByToken(final String token) {
        final String hashedToken = HashRefreshToken.hashToken(token);
        final Optional<RefreshTokenEntity> refreshTokenEntity = refreshTokenRepository.findRefreshTokenEntityByToken(hashedToken);

        return refreshTokenEntity.map(RefreshTokenEntity::toDomainModel);
    }

    @Override
    public RefreshTokenEntity verifyExpiration(final RefreshTokenEntity token) {
        final LocalDateTime now = LocalDateTime.now();

        if(token.getExpiryDate().isBefore(now)){
            refreshTokenRepository.delete(token);
            throw new ExpiredTokenException(token.getToken() + "Refresh token is expired.");
        }

        return token;
    }

    @Override
    public void deleteRefreshToken(final String token) {
        final String hashedToken = HashRefreshToken.hashToken(token);

        refreshTokenRepository.findRefreshTokenEntityByToken(hashedToken)
                .ifPresent(refreshTokenRepository::delete);
    }
}
