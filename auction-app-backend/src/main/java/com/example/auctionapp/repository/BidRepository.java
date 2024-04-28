package com.example.auctionapp.repository;

import com.example.auctionapp.entity.BidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface BidRepository extends JpaRepository<BidEntity, UUID> {
    @Query("SELECT b FROM BidEntity b WHERE b.productEntity.productId = :productId " +
            "AND b.bidAmount = (SELECT MAX(b2.bidAmount) FROM BidEntity b2 " +
            "WHERE b2.productEntity.productId = :productId)")
    BidEntity findHighestBidByProductId(final UUID productId);
}
