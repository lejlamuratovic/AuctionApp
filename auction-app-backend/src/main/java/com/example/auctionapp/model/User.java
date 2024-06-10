package com.example.auctionapp.model;

import com.example.auctionapp.entity.PaymentInfoEntity;
import com.example.auctionapp.entity.enums.UserRoles;

import java.time.LocalDate;
import java.util.UUID;

public class User {
    private UUID userId;
    private String firstName;
    private String lastName;
    private String email;
    private UserRoles role;
    private PaymentInfoEntity paymentInfoEntity;
    private String profilePicture;
    private LocalDate dateOfBirth;
    private boolean isActive;

    public User() { }

    public UUID getUserId() {
        return this.userId;
    }

    public void setUserId(final UUID userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public UserRoles getRole() {
        return this.role;
    }

    public void setRole(final UserRoles role) {
        this.role = role;
    }

    public PaymentInfoEntity getPaymentInfoEntity() {
        return this.paymentInfoEntity;
    }

    public void setPaymentInfoEntity(final PaymentInfoEntity paymentInfoEntity) {
        this.paymentInfoEntity = paymentInfoEntity;
    }

    public String getProfilePicture() {
        return this.profilePicture;
    }

    public void setProfilePicture(final String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public LocalDate getDateOfBirth() {
        return this.dateOfBirth;
    }

    public void setDateOfBirth(final LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean getActive() {
        return this.isActive;
    }

    public void setActive(final boolean active) {
        isActive = active;
    }
}
