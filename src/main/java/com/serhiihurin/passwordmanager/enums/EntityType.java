package com.serhiihurin.passwordmanager.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum EntityType {
    USER("user"),
    RECORD("record"),
    GROUP("group");

    private final String value;
}
