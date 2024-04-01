package com.example.auctionapp.request;

import com.example.auctionapp.entity.ProductEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class ProductAddRequest {

    private String name;
    private String description;
    private BigDecimal startPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String imageUrl;
    private String status;
    private UUID categoryId;

    public ProductAddRequest() {
    }

    public ProductEntity toEntity() {
        ProductEntity productEntity = new ProductEntity();

        productEntity.setName(this.name);
        productEntity.setDescription(this.description);
        productEntity.setStartPrice(this.startPrice);
        productEntity.setStartDate(this.startDate);
        productEntity.setEndDate(this.endDate);
        productEntity.setStatus(this.status);

        // category id is set in services
        return productEntity;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(final BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(final LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(final LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(final UUID categoryId) {
        this.categoryId = categoryId;
    }
}
