package com.example.auctionapp.exceptions.authentication;

import com.example.auctionapp.exceptions.GeneralException;

public class InvalidAccount extends GeneralException {
    public InvalidAccount(String message) {
        super(403, message);
    }

    public InvalidAccount() {
        super(403);
    }
}
