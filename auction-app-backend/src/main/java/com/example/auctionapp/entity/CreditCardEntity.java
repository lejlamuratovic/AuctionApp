package com.example.auctionapp.entity;

import com.example.auctionapp.model.CreditCard;
import com.example.auctionapp.util.builderpattern.GenericBuilder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "credit_card", schema="auction_app")
public class CreditCardEntity {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "credit_card_id")
    private UUID creditCardId;

    @Column(name = "name_on_card")
    private String nameOnCard;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "expiration_date")
    @Temporal(TemporalType.DATE)
    private LocalDate expirationDate;

    @Column(name = "stripe_token")
    private String stripeToken;

    public CreditCardEntity(final UUID creditCardId,
                            final String nameOnCard,
                            final String cardNumber,
                            final LocalDate expirationDate,
                            final String stripeToken
    ) {
        this.creditCardId = creditCardId;
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.stripeToken = stripeToken;
    }

    public CreditCardEntity() {
    }

    public CreditCard toDomainModel() {
        return GenericBuilder.of(CreditCard::new)
                .with(CreditCard::setCreditCardId, this.creditCardId)
                .with(CreditCard::setCardNumber, this.nameOnCard)
                .with(CreditCard::setNameOnCard, this.nameOnCard)
                .with(CreditCard::setStripeToken, this.stripeToken)
                .build();
    }

    public UUID getCreditCardId() {
        return this.creditCardId;
    }

    public void setCreditCardId(final UUID creditCardId) {
        this.creditCardId = creditCardId;
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

    public String getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }
}
