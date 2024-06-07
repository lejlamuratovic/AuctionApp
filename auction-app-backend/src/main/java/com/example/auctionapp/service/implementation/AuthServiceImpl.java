package com.example.auctionapp.service.implementation;

import com.example.auctionapp.entity.RefreshTokenEntity;
import com.example.auctionapp.entity.UserEntity;
import com.example.auctionapp.entity.enums.UserRoles;
import com.example.auctionapp.exceptions.authentication.EmailAlreadyInUseException;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.model.RefreshToken;
import com.example.auctionapp.model.User;
import com.example.auctionapp.repository.UserRepository;
import com.example.auctionapp.request.LoginRequest;
import com.example.auctionapp.request.UserRequest;
import com.example.auctionapp.response.JwtResponse;
import com.example.auctionapp.service.AuthService;
import com.example.auctionapp.service.RefreshTokenService;
import com.example.auctionapp.util.builderpattern.GenericBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthServiceImpl(final UserRepository userRepository,
                           final RefreshTokenService refreshTokenService,
                           final UserDetailsServiceImpl userDetailsService
    ) {
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
    }

    @Override
    public User signUp(final UserRequest userRequest) {
        final Optional<UserEntity> user = userRepository.findUserEntityByEmail(userRequest.getEmail());

        if(user.isPresent()) {
            throw new EmailAlreadyInUseException("This email is already in use");
        }

        final UserEntity userEntity = GenericBuilder.of(UserEntity::new)
                .with(UserEntity::setFirstName, userRequest.getFirstName())
                .with(UserEntity::setLastName, userRequest.getLastName())
                .with(UserEntity::setEmail, userRequest.getEmail())
                .with(UserEntity::setRole, UserRoles.USER)
                .with(UserEntity::setPassword, passwordEncoder.encode(userRequest.getPassword()))
                .build();

        return userRepository.save(userEntity).toDomainModel();
    }

    @Override
    public JwtResponse signIn(final LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        final UserEntity user = userRepository.findUserEntityByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // generate access and refresh tokens
        final String accessToken = jwtService.generateToken(user);
        final String refreshToken = refreshTokenService.createRefreshToken(user.getEmail()).getToken();

        return new JwtResponse(user.getFirstName().concat(" ").concat(user.getLastName()), accessToken, refreshToken);
    }

    @Override
    public String refreshAccessToken(final String token) {
        final RefreshTokenEntity refreshTokenEntity = refreshTokenService.findByToken(token)
                .map(RefreshToken::toEntity)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        final UserEntity user = userRepository.findUserEntityByEmail(refreshTokenEntity.getUserEntity().getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        refreshTokenService.verifyExpiration(refreshTokenEntity);

        return jwtService.generateToken(user);
    }

    @Override
    public void deleteRefreshToken(final String token) {
        refreshTokenService.deleteRefreshToken(token);
    }
}
