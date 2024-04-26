package com.serhiihurin.passwordmanager.dto;

import com.serhiihurin.passwordmanager.entity.Group;
import lombok.Data;

@Data
public class RecordExtendedViewDTO {
    private String recordId;
    private String title;
    private String description;
    private String username;
    private String password;
    private String url;
//    private String groupName;
    private Group group;
}
