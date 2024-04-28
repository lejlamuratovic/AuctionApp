package com.example.auctionapp.repository;

import com.example.auctionapp.entity.BidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.UUID;

public interface BidRepository extends JpaRepository<BidEntity, UUID> {
    @Query("SELECT MAX(b.bidAmount) FROM BidEntity b WHERE b.productEntity.productId = :productId")
    BigDecimal findHighestBidByProductId(final UUID productId);
}
