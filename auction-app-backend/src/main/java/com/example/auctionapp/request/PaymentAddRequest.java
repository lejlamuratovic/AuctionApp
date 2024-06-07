package com.example.auctionapp.request;

import com.example.auctionapp.entity.PaymentInfoEntity;
import com.example.auctionapp.util.annotation.LuhnCheck;
import com.example.auctionapp.util.builderpattern.GenericBuilder;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.UUID;

public class PaymentAddRequest {
    @NotEmpty(message = "Address is required")
    @Pattern(regexp = "^[a-zA-Z0-9\\s,.'-]{3,100}$", message = "Invalid address")
    private String address;

    @NotEmpty(message = "City is required")
    @Pattern(regexp = "^[a-zA-Z\\s]{3,50}$", message = "Invalid city")
    private String city;

    @NotEmpty(message = "Zip code is required")
    @Pattern(regexp = "^[0-9]{5,10}$", message = "Invalid zip code")
    private String zipCode;

    @NotEmpty(message = "Country is required")
    @Pattern(regexp = "^[a-zA-Z\\s]{3,50}$", message = "Invalid country")
    private String country;

    @NotEmpty(message = "Name on card is required")
    @Size(min = 2, message = "Name on card must be at least 2 characters long")
    private String nameOnCard;

    @LuhnCheck
    @Size(min = 14, message = "Credit card number should be a valid 14-digit number")
    @NotEmpty(message = "Card number is required")
    private String cardNumber;

    @Future(message = "Expiration date must be in the future")
    private LocalDate expirationDate;
    private UUID productId;
    private UUID buyerId;

    public PaymentAddRequest(final String address,
                             final String city,
                             final String country,
                             final String zipCode, 
                             final String nameOnCard,
                             final String cardNumber,
                             final LocalDate expirationDate) {
        this.address = address;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
    }

    public PaymentInfoEntity toEntity() {
        return GenericBuilder.of(PaymentInfoEntity::new)
                .with(PaymentInfoEntity::setCity, this.city)
                .with(PaymentInfoEntity::setCountry, this.country)
                .with(PaymentInfoEntity::setAddress, this.address)
                .with(PaymentInfoEntity::setZipCode, this.zipCode)
                .build();
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

    public String getNameOnCard() {
        return this.nameOnCard;
    }

    public void setNameOnCard(final String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(final String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }

    public void setExpirationDate(final LocalDate expirationDate) {
        this.expirationDate = expirationDate;
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
