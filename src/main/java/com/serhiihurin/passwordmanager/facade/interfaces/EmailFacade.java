package com.serhiihurin.passwordmanager.facade.interfaces;

public interface EmailFacade {
    void sendGreetingsEmail(String toEmail, String name);

    String sendPasswordChangingVerificationCode(String toEmail);

    void sendEmailChangingVerificationCode(String toEmail);
}
