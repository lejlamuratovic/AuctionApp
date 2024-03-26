package com.example.auctionapp.repository;

import com.example.auctionapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("SELECT p.productId FROM Product p")
    List<UUID> findProductIds();
}
