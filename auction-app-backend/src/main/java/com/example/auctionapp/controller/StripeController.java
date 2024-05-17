package com.example.auctionapp.controller;

import com.example.auctionapp.request.ChargeRequest;
import com.example.auctionapp.service.implementation.StripeService;
import com.stripe.exception.StripeException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stripe")
@SecurityRequirement(name = "JWT Security")
public class StripeController {
    private static final Logger logger = LoggerFactory.getLogger(StripeController.class);
    private final StripeService stripeService;

    public StripeController(final StripeService stripeService) {
        this.stripeService = stripeService;
    }

    @PostMapping("/checkout/integrated")
    public String integratedCheckout(@RequestBody final ChargeRequest request) {
        try {
            logger.info("Processing integrated checkout for customer: {}", request.getCustomerEmail());

            String clientSecret = stripeService.createPaymentIntent(request);

            logger.info("Checkout processed successfully for customer: {}", request.getCustomerEmail());

            return clientSecret;
        } catch (StripeException e) {
            logger.error("Failed integrated checkout: {}", e.getMessage(), e);

            throw new RuntimeException("Payment processing failed.");
        }
    }
}
