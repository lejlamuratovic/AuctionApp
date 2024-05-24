package com.example.auctionapp.service;

import com.example.auctionapp.model.PaymentInfo;
import com.example.auctionapp.request.PaymentAddRequest;
import com.example.auctionapp.request.StripePaymentAddRequest;

import java.util.UUID;

public interface PaymentService {
    PaymentInfo addNewPaymentInfo(final StripePaymentAddRequest stripePaymentAddRequest);
    PaymentInfo addNewPaymentInfo(final PaymentAddRequest paymentAddRequest);
}
