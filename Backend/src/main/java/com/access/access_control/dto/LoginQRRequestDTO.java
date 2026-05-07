package com.access.access_control.dto;

public class LoginQRRequestDTO {

    private String qrToken;
    public LoginQRRequestDTO() {}

    public String getQrToken() { return qrToken; }
    public void setQrToken(String qrToken) { this.qrToken = qrToken; }
}
