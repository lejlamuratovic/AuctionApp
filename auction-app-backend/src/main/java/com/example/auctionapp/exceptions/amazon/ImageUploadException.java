package com.example.auctionapp.exceptions.amazon;

import com.example.auctionapp.exceptions.GeneralException;

public class ImageUploadException extends GeneralException {
    public ImageUploadException(String message) {
        super(500, message);
    }

    public ImageUploadException() {
        super(500);
    }
}
