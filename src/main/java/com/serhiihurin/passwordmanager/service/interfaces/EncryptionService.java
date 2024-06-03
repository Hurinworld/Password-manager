package com.serhiihurin.passwordmanager.service.interfaces;

public interface EncryptionService {
    String encrypt(String input);

    String decrypt(String cipherText);
}
