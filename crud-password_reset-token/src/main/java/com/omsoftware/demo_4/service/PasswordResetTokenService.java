package com.omsoftware.demo_4.service;

public interface PasswordResetTokenService {
    Object forgotPassword(String email);

    Object resetPassword(String token, String password);
}
