package com.example.auctionapp.model;

import java.util.UUID;

public class BoughtProduct {
    private UUID boughtProductId;
    private UUID productId;
    private UUID buyerId;
    private UUID paymentInfoId;

    public UUID getBoughtProductId() {
        return boughtProductId;
    }

    public void setBoughtProductId(UUID boughtProductId) {
        this.boughtProductId = boughtProductId;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public UUID getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(UUID buyerId) {
        this.buyerId = buyerId;
    }

    public UUID getPaymentInfoIid() {
        return paymentInfoId;
    }

    public void setPaymentInfoIid(UUID paymentInfoId) {
        this.paymentInfoId = paymentInfoId;
    }
}
