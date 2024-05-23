package com.example.auctionapp.controller;

import com.example.auctionapp.request.StripePaymentAddRequest;
import com.example.auctionapp.service.PaymentService;
import com.example.auctionapp.util.SecurityRoles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payment")
@SecurityRequirement(name = "JWT Security")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(final PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PreAuthorize(SecurityRoles.ALL)
    @PostMapping("/add-payment-info")
    public void addPaymentInformtation(@RequestBody final StripePaymentAddRequest stripePaymentAddRequest) {
         // Nothing to do here yet
    }
}
