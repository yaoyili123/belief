package com.example.belief.data.network.model;

public class ApiFault extends RuntimeException {

    private int status;

    private String message;

    public ApiFault(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getErrorCode() {
        return status;
    }

    @Override
    public String getMessage() {
        return status + ": " + message;
    }
}
