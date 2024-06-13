package com.example.auctionapp.exceptions.security;

import com.example.auctionapp.exceptions.GeneralException;
import org.springframework.http.HttpStatus;

public class InvalidTokenException extends GeneralException {
    public InvalidTokenException(String message) {
        super(401, message);
    }

    public InvalidTokenException() {
        super(401);
    }
}
