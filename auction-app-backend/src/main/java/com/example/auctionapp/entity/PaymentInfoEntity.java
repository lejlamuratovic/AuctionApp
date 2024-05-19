package com.example.auctionapp.entity;

import com.example.auctionapp.model.PaymentInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "payment_info", schema="auction_app")
public class PaymentInfoEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "payment_info_id")
    private UUID paymentInfoId;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "zip_code")
    private String zipCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "credit_card_id")
    private CreditCardEntity creditCardEntity;

    public PaymentInfoEntity() {

    }

    public PaymentInfo toDomainModel() {
        PaymentInfo paymentInfo = new PaymentInfo();

        paymentInfo.setPaymentInfoId(this.paymentInfoId);
        paymentInfo.setAddress(this.address);
        paymentInfo.setCity(this.city);
        paymentInfo.setCountry(this.country);
        paymentInfo.setZipCode(this.zipCode);

        return paymentInfo;
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

    public CreditCardEntity getCreditCardEntity() {
        return this.creditCardEntity;
    }

    public void setCreditCardEntity(final CreditCardEntity creditCardEntity) {
        this.creditCardEntity = creditCardEntity;
    }
}
