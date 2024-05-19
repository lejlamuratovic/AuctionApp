package com.example.auctionapp.entity;

import com.example.auctionapp.model.CreditCard;
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

    public CreditCardEntity(final UUID creditCardId,
                            final String nameOnCard,
                            final String cardNumber,
                            final LocalDate expirationDate) {
        this.creditCardId = creditCardId;
        this.nameOnCard = nameOnCard;
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
    }

    public CreditCard toDomainModel() {
        CreditCard creditCard = new CreditCard();

        creditCard.setCreditCardId(this.creditCardId);
        creditCard.setCardNumber(this.cardNumber);
        creditCard.setExpirationDate(this.expirationDate);
        creditCard.setNameOnCard(this.nameOnCard);

        return creditCard;
    }
}
