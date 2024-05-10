package com.example.auctionapp.request;

import com.example.auctionapp.entity.ProductEntity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class ProductAddRequest {
    private String name;
    private String description;
    private BigDecimal startPrice;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String status;
    private UUID categoryId;
    private UUID userId;
    private String nameOnCard;
    private String cardNumber;
    private LocalDate expirationDate;
    private String zipCode;
    private String city;
    private String address;
    private String country;

    // flag to use existing payment info
    private boolean useExistingPaymentInfo;

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

        return productEntity;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
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

    public String getStatus() {
        return this.status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public UUID getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(final UUID categoryId) {
        this.categoryId = categoryId;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public void setUserId(final UUID userId) {
        this.userId = userId;
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

    public String getZipCode() {
        return this.zipCode;
    }

    public void setZipCode(final String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(final String address) {
        this.address = address;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(final String country) {
        this.country = country;
    }

    public boolean isUseExistingPaymentInfo() {
        return this.useExistingPaymentInfo;
    }

    public void setUseExistingPaymentInfo(final boolean useExistingPaymentInfo) {
        this.useExistingPaymentInfo = useExistingPaymentInfo;
    }
}
