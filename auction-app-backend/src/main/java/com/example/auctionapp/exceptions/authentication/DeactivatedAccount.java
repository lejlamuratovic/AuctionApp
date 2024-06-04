package com.example.auctionapp.exceptions.authentication;

import com.example.auctionapp.exceptions.GeneralException;

public class DeactivatedAccount extends GeneralException {
    public DeactivatedAccount(String message) {
        super(403, message);
    }

    public DeactivatedAccount() {
        super(403);
    }
}
