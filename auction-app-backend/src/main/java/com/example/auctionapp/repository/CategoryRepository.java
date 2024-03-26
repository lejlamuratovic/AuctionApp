package com.example.auctionapp.repository;

import com.example.auctionapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;


public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByParentCategoryIsNull();
}
