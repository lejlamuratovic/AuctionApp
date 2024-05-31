package com.example.auctionapp.repository;

import com.example.auctionapp.entity.BoughtProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BoughtProductRepository extends JpaRepository<BoughtProductEntity, UUID> {
}
