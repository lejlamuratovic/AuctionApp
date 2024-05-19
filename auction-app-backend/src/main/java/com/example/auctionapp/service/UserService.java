package com.example.auctionapp.service;

import com.example.auctionapp.model.CreditCard;
import com.example.auctionapp.model.PaymentInfo;
import com.example.auctionapp.model.User;
import com.example.auctionapp.request.CreditCardAddRequest;
import com.example.auctionapp.request.PaymentAddRequest;

import java.util.UUID;

public interface UserService {
    User getUser(final UUID userId);
    CreditCard updateCreditCardDetails(final UUID userId, final CreditCardAddRequest addNewCreditCard);
    PaymentInfo addOrUpdatePaymentInfo(final UUID userId, final PaymentAddRequest paymentRequest);
}
