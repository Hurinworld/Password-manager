package com.serhiihurin.passwordmanager.service.interfaces;

import com.serhiihurin.passwordmanager.entity.User;

import java.util.Optional;

public interface EncryptionService {
    String encrypt(String input, Optional<User> anonymousUser);

    String decrypt(String cipherText, Optional<User> anonymousUser);
}
