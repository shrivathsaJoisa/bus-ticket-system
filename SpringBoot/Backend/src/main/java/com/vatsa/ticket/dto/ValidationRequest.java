package com.vatsa.ticket.dto;

public class ValidationRequest {

    private String qrToken;

    public ValidationRequest() {
    }

    public ValidationRequest(String qrToken) {
        this.qrToken = qrToken;
    }

    public String getQrToken() {
        return qrToken;
    }

    public void setQrToken(String qrToken) {
        this.qrToken = qrToken;
    }
}
