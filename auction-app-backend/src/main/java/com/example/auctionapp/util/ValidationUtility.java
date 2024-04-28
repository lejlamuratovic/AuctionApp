package com.example.auctionapp.util;

import com.example.auctionapp.entity.ProductEntity;
import com.example.auctionapp.exceptions.validation.ValidationException;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ValidationUtility {
    public static void validateBidAmount(final BigDecimal bidAmount, final ProductEntity product) {
        if (bidAmount.compareTo(product.getStartPrice()) < 0) {
            throw new ValidationException("Bid amount cannot be less than the product's start price.");
        }
    }

    public static void validateBidTime(final LocalDateTime bidTime, final ProductEntity product) {
        if (bidTime.isAfter(product.getEndDate())) {
            throw new ValidationException("Bid cannot be placed after the product's end date.");
        }
    }
}
