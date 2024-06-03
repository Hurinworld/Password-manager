package com.serhiihurin.passwordmanager.service;

import com.serhiihurin.passwordmanager.service.interfaces.AuthenticationService;
import com.serhiihurin.passwordmanager.service.interfaces.EncryptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
@RequiredArgsConstructor
@Slf4j
public class EncryptionServiceImpl implements EncryptionService {
    private final AuthenticationService authenticationService;
    private final String algorithm = "AES/CBC/PKCS5Padding";
    private final IvParameterSpec iv = loadIv();

    @Override
    public String encrypt(String input) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, getKey(authenticationService.getAuthenticatedUser().getPassword()), iv);
            byte[] cipherText = cipher.doFinal(input.getBytes());
            return Base64.getEncoder()
                    .encodeToString(cipherText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String decrypt(String cipherText) {
        log.info(authenticationService.getAuthenticatedUser().getPassword());
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, getKey(authenticationService.getAuthenticatedUser().getPassword()), iv);
            byte[] plainText = cipher.doFinal(Base64.getDecoder()
                    .decode(cipherText));
            return new String(plainText);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                 InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }
    }

    private SecretKey getKey(String input) {
        int midIndex = input.length() / 2;
        String firstHalf = input.substring(0, midIndex);
        String secondHalf = input.substring(midIndex);

        String rearrangedFirstHalf = rearrangeString(firstHalf);
        String rearrangedSecondHalf = rearrangeString(secondHalf);

        String combined = rearrangedFirstHalf + rearrangedSecondHalf;

        if (combined.length() > 32) {
            combined = combined.substring(0, 32);
        }

        return new SecretKeySpec(combined.getBytes(), "AES");
    }

    private String rearrangeString(String str) {
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length - 1; i += 2) {
            char temp = chars[i];
            chars[i] = chars[i + 1];
            chars[i + 1] = temp;
        }
        return new String(chars);
    }

    private IvParameterSpec loadIv() {
        try {
            byte[] ivBytes = Files.readAllBytes(Paths.get("iv.key"));
            return new IvParameterSpec(ivBytes);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load IV", e);
        }
    }
}
