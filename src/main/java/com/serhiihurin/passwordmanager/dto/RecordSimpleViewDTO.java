package com.serhiihurin.passwordmanager.dto;

import lombok.Data;

@Data
public class RecordSimpleViewDTO {
    private String recordId;
    private String title;
    private String description;
    private String url;
    private String group;
}
