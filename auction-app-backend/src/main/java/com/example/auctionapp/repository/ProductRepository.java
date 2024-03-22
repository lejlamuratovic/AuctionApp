package com.example.auctionapp.repository;

import com.example.auctionapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Nothing to do here
}
