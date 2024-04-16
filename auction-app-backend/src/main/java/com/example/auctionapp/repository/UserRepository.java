package com.example.auctionapp.repository;

import com.example.auctionapp.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
    // nothing to do here
}
