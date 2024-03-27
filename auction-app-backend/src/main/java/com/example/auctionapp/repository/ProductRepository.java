package com.example.auctionapp.repository;

import com.example.auctionapp.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    @Query("SELECT p.productId FROM ProductEntity p")
    List<UUID> findProductIds();
}
