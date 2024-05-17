package com.example.auctionapp.request;

import java.util.Currency;

public class ChargeRequest {
    private String description;
    private int amount;
    private Currency currency;
    private String stripeEmail;
    private String stripeToken;

    public ChargeRequest(final String description,
                         final int amount,
                         final Currency currency,
                         final String stripeEmail,
                         final String stripeToken) {
        this.description = description;
        this.amount = amount;
        this.currency = currency;
        this.stripeEmail = stripeEmail;
        this.stripeToken = stripeToken;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public int getAmount() {
        return this.amount;
    }

    public void setAmount(final int amount) {
        this.amount = amount;
    }

    public Currency getCurrency() {
        return this.currency;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public String getStripeEmail() {
        return this.stripeEmail;
    }

    public void setStripeEmail(final String stripeEmail) {
        this.stripeEmail = stripeEmail;
    }

    public String getStripeToken() {
        return this.stripeToken;
    }

    public void setStripeToken(final String stripeToken) {
        this.stripeToken = stripeToken;
    }
}
