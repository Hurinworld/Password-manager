package com.serhiihurin.passwordmanager.dto;

import lombok.Data;

@Data
public class RecordExtendedViewDTO {
    private Long recordId;
    private String title;
    private String description;
    private String username;
    private String password;
    private String url;
    private String groupName;
}
