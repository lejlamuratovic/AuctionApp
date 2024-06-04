package com.example.auctionapp.exceptions.authentication;

import com.example.auctionapp.exceptions.GeneralException;

public class CannotDeactivateAccount extends GeneralException {
    public CannotDeactivateAccount(String message) {
        super(409, message);
    }

    public CannotDeactivateAccount() {
        super(409);
    }
}
