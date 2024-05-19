package com.example.auctionapp.controller;

import com.example.auctionapp.model.CreditCard;
import com.example.auctionapp.model.PaymentInfo;
import com.example.auctionapp.model.User;
import com.example.auctionapp.request.CreditCardAddRequest;
import com.example.auctionapp.request.PaymentAddRequest;
import com.example.auctionapp.service.UserService;
import com.example.auctionapp.util.SecurityRoles;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@SecurityRequirement(name = "JWT Security")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize(SecurityRoles.ALL)
    @GetMapping("/{userId}")
    public User getUser(@PathVariable final UUID userId) {
        return userService.getUser(userId);
    }

    @PreAuthorize(SecurityRoles.ALL)
    @PostMapping("/{userId}/payment-info")
    public PaymentInfo addPaymentInfoToUser(@PathVariable final UUID userId,
                                            @RequestBody final PaymentAddRequest paymentAddRequest) {
        return userService.addOrUpdatePaymentInfo(userId, paymentAddRequest);
    }

    @PreAuthorize(SecurityRoles.ALL)
    @PostMapping("/{userId}/credit-card")
    public CreditCard updateCreditCardDetails(@PathVariable final UUID userId,
                                              @RequestBody final CreditCardAddRequest creditCardAddRequest) {
        return userService.updateCreditCardDetails(userId, creditCardAddRequest);
    }
}
