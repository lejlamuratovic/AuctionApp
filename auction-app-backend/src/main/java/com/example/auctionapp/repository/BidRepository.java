package com.example.auctionapp.repository;

import com.example.auctionapp.entity.BidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;
import java.util.List;

public interface BidRepository extends JpaRepository<BidEntity, UUID> {
    BidEntity findTopBidEntityByProductEntity_ProductIdOrderByBidTimeDesc(final UUID productId);
    List<BidEntity> findBidEntitiesByUserEntity_UserId(final UUID userId);
    @Query("SELECT b FROM BidEntity b " +
            "WHERE b.userEntity.userId = :userId AND b.bidAmount = (SELECT MAX(b2.bidAmount) " +
            "FROM BidEntity b2 WHERE b2.productEntity.productId = b.productEntity.productId)")
    List<BidEntity> findDistinctHighestBidsByUserId(UUID userId);
}
