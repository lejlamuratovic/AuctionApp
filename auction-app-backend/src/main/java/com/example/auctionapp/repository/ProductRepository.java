package com.example.auctionapp.repository;

import com.example.auctionapp.entity.ProductEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {

    @Query(value = "SELECT p FROM ProductEntity p ORDER BY RANDOM() LIMIT 1")
    Optional<ProductEntity> findRandomProduct();
    Page<ProductEntity> findByCategoryEntityCategoryId(UUID categoryId, Pageable pageable);
    Page<ProductEntity> findByNameContainingIgnoreCaseAndCategoryEntityCategoryId(String searchQuery, UUID categoryId, Pageable page);
    Page<ProductEntity> findByNameContainingIgnoreCase(String searchQuery, Pageable page);
}
