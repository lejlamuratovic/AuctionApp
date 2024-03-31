package com.example.auctionapp.request;

import com.example.auctionapp.entity.ProductImageEntity;

import java.util.UUID;

public class ProductImageAddRequest {

    private String imageUrl;
    private UUID productId;

    public ProductImageAddRequest() {
    }

    public ProductImageEntity toEntity() {
        ProductImageEntity productImageEntity = new ProductImageEntity();

        productImageEntity.setImageUrl(this.imageUrl);
        // product id in services

        return productImageEntity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(final String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(final UUID productId) {
        this.productId = productId;
    }
}
