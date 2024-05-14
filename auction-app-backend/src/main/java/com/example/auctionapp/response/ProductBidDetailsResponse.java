package com.example.auctionapp.response;

import com.example.auctionapp.entity.enums.ProductStatus;
import com.example.auctionapp.model.ProductImage;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class ProductBidDetailsResponse {
    private UUID id;
    private String name;
    private BigDecimal startPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private ProductStatus status;
    private List<ProductImage> productImages;
    private UUID userId;
    private BigDecimal bidAmount;
    private int bidsCount;
    private BigDecimal highestBid;

    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public BigDecimal getStartPrice() {
        return this.startPrice;
    }

    public void setStartPrice(final BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public LocalDateTime getStartDate() {
        return this.startDate;
    }

    public void setStartDate(final LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return this.endDate;
    }

    public void setEndDate(final LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public ProductStatus getStatus() {
        return this.status;
    }

    public void setStatus(final ProductStatus status) {
        this.status = status;
    }

    public List<ProductImage> getProductImages() {
        return this.productImages;
    }

    public void setProductImages(final List<ProductImage> productImages) {
        this.productImages = productImages;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public void setUserId(final UUID userId) {
        this.userId = userId;
    }

    public BigDecimal getBidAmount() {
        return this.bidAmount;
    }

    public void setBidAmount(final BigDecimal bidAmount) {
        this.bidAmount = bidAmount;
    }

    public int getBidsCount() {
        return this.bidsCount;
    }

    public void setBidsCount(final int bidsCount) {
        this.bidsCount = bidsCount;
    }

    public BigDecimal getHighestBid() {
        return this.highestBid;
    }

    public void setHighestBid(final BigDecimal highestBid) {
        this.highestBid = highestBid;
    }
}
