package com.example.auctionapp.repository;

import com.example.auctionapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    // Nothing to do here
}
