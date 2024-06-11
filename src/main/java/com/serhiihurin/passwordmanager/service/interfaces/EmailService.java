package com.serhiihurin.passwordmanager.service.interfaces;

public interface EmailService {
    void sendGreetingsEmail(String toEmail, String name);

    void sendPasswordChangingVerificationCode(String toEmail, String verificationCode);

    void sendEmailChangingVerificationCode(String toEmail);
}
