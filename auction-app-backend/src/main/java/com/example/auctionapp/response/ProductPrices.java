package com.example.auctionapp.response;

import java.math.BigDecimal;

public class ProductPrices {
    private BigDecimal minPrice;
    private BigDecimal maxPrice;

    public BigDecimal getMinPrice() {
        return this.minPrice;
    }

    public void setMinPrice(final BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return this.maxPrice;
    }

    public void setMaxPrice(final BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }
}
