package com.example.auctionapp.repository;

import com.example.auctionapp.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<NotificationEntity, UUID> {
}
