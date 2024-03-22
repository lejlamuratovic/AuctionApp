package com.example.auctionapp.repository;

import com.example.auctionapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Nothing to do here
}