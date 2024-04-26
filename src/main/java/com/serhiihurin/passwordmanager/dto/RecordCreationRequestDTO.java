package com.serhiihurin.passwordmanager.dto;

import lombok.Data;

@Data
public class RecordCreationRequestDTO {
    private String title;
    private String description;
    private String username;
    private String password;
    private String url;
}
