package com.example.auctionapp.model;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

public class PaymentInfo {
    private UUID paymentInfoId;
    private String address;
    private String city;
    private String country;
    private String zipCode;

    public PaymentInfo() {
    }

    public UUID getPaymentInfoId() {
        return this.paymentInfoId;
    }

    public void setPaymentInfoId(final UUID paymentInfoId) {
        this.paymentInfoId = paymentInfoId;
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
}
