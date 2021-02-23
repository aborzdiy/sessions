package com.example.payments.controller;

public class AuthRequest {
    private String login;
    private String password;
    private String deviceId;

    public AuthRequest() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    @Override
    public String toString() {
        return "AuthRequest{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", tokenId='" + deviceId + '\'' +
                '}';
    }
}
