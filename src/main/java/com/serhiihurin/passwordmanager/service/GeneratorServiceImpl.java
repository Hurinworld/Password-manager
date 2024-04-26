package com.serhiihurin.passwordmanager.service;

import com.serhiihurin.passwordmanager.enums.EntityType;
import com.serhiihurin.passwordmanager.service.interfaces.GeneratorService;
import org.springframework.stereotype.Service;

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
        if (generatedId.equals("")) {
            throw new RuntimeException("Error while generating entity ID");
        }
        return generatedId;
    }

    @Override
    public String generatePassword() {
        return null;
    }
}
