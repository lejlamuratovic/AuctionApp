package com.example.auctionapp.service.implementation;

import com.example.auctionapp.entity.UserEntity;
import com.example.auctionapp.exceptions.repository.ResourceNotFoundException;
import com.example.auctionapp.model.User;
import com.example.auctionapp.repository.UserRepository;
import com.example.auctionapp.request.LoginRequest;
import com.example.auctionapp.request.UserRequest;
import com.example.auctionapp.response.LoginResponse;
import com.example.auctionapp.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User signUp(UserRequest userRequest) {
        UserEntity userEntity = new UserEntity();

        userEntity.setFirstName(userRequest.getFirstName());
        userEntity.setLastName(userRequest.getLastName());
        userEntity.setEmail(userRequest.getEmail());
        userEntity.setRole(userRequest.getRole());
        userEntity.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return userRepository.save(userEntity).toDomainModel();
    }

    @Override
    public LoginResponse signIn(LoginRequest loginRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );
        final UserEntity user = userRepository.findUserEntityByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("This user does not exist."));

        return new LoginResponse(jwtService.generateToken(user));
    }
}
