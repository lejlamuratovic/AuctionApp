package com.example.auctionapp.repository;

import com.example.auctionapp.entity.BoughtProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BoughtProductRepository extends JpaRepository<BoughtProductEntity, UUID> {
    List<BoughtProductEntity> findBoughtProductEntitiesByUserEntity_UserId(final UUID userId);
}
