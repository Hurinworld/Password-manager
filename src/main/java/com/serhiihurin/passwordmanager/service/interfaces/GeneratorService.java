package com.serhiihurin.passwordmanager.service.interfaces;

import com.serhiihurin.passwordmanager.enums.EntityType;

public interface GeneratorService {
    String generateEntityId(EntityType entityType);

    String generatePassword();
}
