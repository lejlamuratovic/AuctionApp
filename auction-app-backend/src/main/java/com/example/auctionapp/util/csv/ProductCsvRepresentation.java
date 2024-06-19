package com.example.auctionapp.util.csv;

import com.example.auctionapp.util.annotation.LuhnCheck;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class ProductCsvRepresentation {
    @NotEmpty(message = "Product name is required")
    @Size(min = 2, max = 100, message = "Product name must be between 2 and 100 characters long")
    private String name;

    @NotEmpty(message = "Description is required")
    @Size(min = 10, max = 700, message = "Description must be between 10 and 700 characters long")
    private String description;

    @NotNull(message = "Start price is required")
    private String startPrice;

    @NotEmpty(message = "Start date is required")
    private String startDate;

    @NotEmpty(message = "End date is required")
    private String endDate;

    @NotNull(message = "Category is required")
    private String categoryName;

    @NotEmpty(message = "Name on card is required")
    @Size(min = 2, message = "Name on card must be at least 2 characters long")
    private String nameOnCard;

    @LuhnCheck
    @Size(min = 14, message = "Credit card number should be a valid 14-digit number")
    @NotEmpty(message = "Card number is required")
    private String cardNumber;

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

    @NotEmpty(message = "Images are required")
    private String images;

    @NotEmpty(message = "Expiration date is required")
    private String expirationDate;

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

    public String getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(String startPrice) {
        this.startPrice = startPrice;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getNameOnCard() {
        return nameOnCard;
    }

    public void setNameOnCard(String nameOnCard) {
        this.nameOnCard = nameOnCard;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
