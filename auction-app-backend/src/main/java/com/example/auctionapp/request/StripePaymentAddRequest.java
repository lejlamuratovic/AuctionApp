package com.example.auctionapp.request;

import com.example.auctionapp.entity.PaymentInfoEntity;

import java.util.UUID;

public class StripePaymentAddRequest {
    private String address;
    private String email;
    private String city;
    private String country;
    private String zipCode;
    private String stripeToken;
    private UUID productId;
    private UUID buyerId;

    public PaymentInfoEntity toEntity() {
        PaymentInfoEntity entity = new PaymentInfoEntity();

        entity.setCity(this.city);
        entity.setCountry(this.country);
        entity.setAddress(this.address);
        entity.setZipCode(this.zipCode);

        return entity;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(final String zipCode) {
        this.zipCode = zipCode;
    }

    public String getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(final String stripeToken) {
        this.stripeToken = stripeToken;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public UUID getProductId() {
        return this.productId;
    }

    public void setProductId(final UUID productId) {
        this.productId = productId;
    }

    public UUID getBuyerId() {
        return this.buyerId;
    }

    public void setBuyerId(final UUID buyerId) {
        this.buyerId = buyerId;
    }
}
