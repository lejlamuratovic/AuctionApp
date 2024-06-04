package com.example.auctionapp.request;

import com.example.auctionapp.util.annotation.LuhnCheck;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UserDetailsRequest {
    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Last name is required")
    private String lastName;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotEmpty(message = "Password is required")
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9]).{8,}$",
            message = """
                    Password must contain at least one uppercase letter,
                    one lowercase letter, one digit, and be at least 8 characters long.
                    """)
    private String password;

    @NotEmpty(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the future")
    private LocalDate dateOfBirth;

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

    @NotEmpty(message = "State is required")
    @Pattern(regexp = "^[a-zA-Z\\s]{3,50}$", message = "Invalid country")
    private String state;

    @NotEmpty(message = "Name on card is required")
    @Size(min = 2, message = "Name on card must be at least 2 characters long")
    private String nameOnCard;

    @LuhnCheck
    @Size(min = 14, message = "Credit card number should be a valid 14-digit number")
    @NotEmpty(message = "Card number is required")
    private String cardNumber;

    @Future(message = "Expiration date must be in the future")
    private LocalDate expirationDate;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public String getState() {
        return this.state;
    }

    public void setState(final String state) {
        this.state = state;
    }
}
