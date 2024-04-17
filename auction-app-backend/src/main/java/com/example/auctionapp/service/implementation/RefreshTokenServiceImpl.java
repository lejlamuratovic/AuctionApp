package com.example.auctionapp.service.implementation;

import com.example.auctionapp.entity.RefreshTokenEntity;
import com.example.auctionapp.entity.UserEntity;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.model.RefreshToken;
import com.example.auctionapp.repository.RefreshTokenRepository;
import com.example.auctionapp.repository.UserRepository;
import com.example.auctionapp.service.RefreshTokenService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        RefreshTokenEntity refreshToken = new RefreshTokenEntity();
        final Optional<UserEntity> user = userRepository.findUserEntityByEmail(username);

        if(user.isEmpty()) {
            throw new ResourceNotFoundException("User does not exist.");
        }

        refreshToken.setUserEntity(user.get());
        refreshToken.setToken(UUID.randomUUID().toString()); // generate and set token
        refreshToken.setExpiryDate(LocalDateTime.now().plusMinutes(10)); // expiry of refresh token to 10 minutes

        return refreshTokenRepository.save(refreshToken).toDomainModel();
    }

    @Override
    public Optional<RefreshToken> findByToken(final String token) {
        Optional<RefreshTokenEntity> refreshTokenEntity = refreshTokenRepository.findRefreshTokenEntityByToken(token);

        return refreshTokenEntity.map(RefreshTokenEntity::toDomainModel);
    }

    @Override
    public RefreshToken verifyExpiration(final RefreshTokenEntity token) {
        LocalDateTime now = LocalDateTime.now();

        if(token.getExpiryDate().isBefore(now)){
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + "Refresh token is expired.");
        }

        return token.toDomainModel();
    }
}
