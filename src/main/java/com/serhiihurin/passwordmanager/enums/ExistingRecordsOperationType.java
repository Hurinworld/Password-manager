package com.serhiihurin.passwordmanager.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExistingRecordsOperationType {
    ENCRYPT("encrypt"),
    DECRYPT("decrypt");

    private final String value;
}
