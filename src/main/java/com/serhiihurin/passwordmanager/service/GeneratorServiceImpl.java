package com.serhiihurin.passwordmanager.service;

import com.serhiihurin.passwordmanager.enums.EntityType;
import com.serhiihurin.passwordmanager.service.interfaces.GeneratorService;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class GeneratorServiceImpl implements GeneratorService {
    @Override
    public String generateEntityId(EntityType entityType) {
        String generatedId = "";
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        int idLength = 20;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        for (int i = 0; i < idLength; i++) {

            int randomIndex = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(randomIndex));
        }

        switch (entityType) {
            case USER -> generatedId = "uid_" + stringBuilder;
            case RECORD -> generatedId = "rid_" + stringBuilder;
            case GROUP -> generatedId = "gid_" + stringBuilder;
        }
        if (generatedId.isEmpty()) {
            throw new RuntimeException("Error while generating entity ID");
        }
        return generatedId;
    }

    @Override
    public String generatePassword(
            String length,
            boolean isUpperCaseIncluded,
            boolean isNumbersIncluded,
            boolean isSpecialCharactersIncluded
    ) {
        String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
        String NUMBERS = "0123456789";
        String SPECIAL_CHARACTERS = "*$\\|/!@#^?&:;*()-_+=.,'";

        int passwordLength;
        try {
            passwordLength = Integer.parseInt(length);
        } catch (NumberFormatException e) {
            Notification.show("Invalid length");
            return null;
        }

        String generationCharacters = LOWER_CASE;

        if (isUpperCaseIncluded) {
            generationCharacters += UPPER_CASE;
        }
        if (isNumbersIncluded) {
            generationCharacters += NUMBERS;
        }
        if (isSpecialCharactersIncluded) {
            generationCharacters += SPECIAL_CHARACTERS;
        }

        SecureRandom random = new SecureRandom();
        StringBuilder generatedPassword = new StringBuilder();
        for (int i = 0; i < passwordLength; i++) {
            generatedPassword.append(
                    generationCharacters.charAt(
                            random.nextInt(generationCharacters.length())
                    )
            );
        }

        return generatedPassword.toString();
    }
}
