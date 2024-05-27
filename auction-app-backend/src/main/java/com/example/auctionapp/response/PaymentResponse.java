package com.example.auctionapp.response;

public class PaymentResponse {
    private boolean success;
    private String clientSecret;
    private String errorMessage;

    public PaymentResponse(final String clientSecret) {
        this.success = true;
        this.clientSecret = clientSecret;
        this.errorMessage = null;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    public void setClientSecret(final String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public String getClientSecret() {
        return this.clientSecret;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }
}
