package com.example.auctionapp.model;

import com.example.auctionapp.entity.CategoryEntity;
import com.example.auctionapp.entity.ProductEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Product {

    private UUID id;
    private String name;
    private String description;
    private BigDecimal startPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String imageUrl;
    private String status;
    private UUID categoryId;

    public Product() {
    }

    public static Product toDomainModel(ProductEntity productEntity) {
        Product product = new Product();
        product.setId(productEntity.getProductId());
        product.setName(productEntity.getName());
        product.setDescription(productEntity.getDescription());
        product.setStartPrice(productEntity.getStartPrice());
        product.setStartDate(productEntity.getStartDate());
        product.setEndDate(productEntity.getEndDate());
        product.setImageUrl(productEntity.getImageUrl());
        product.setStatus(productEntity.getStatus());
        product.categoryId = productEntity.getCategory().getCategoryId();
        return product;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }
}
