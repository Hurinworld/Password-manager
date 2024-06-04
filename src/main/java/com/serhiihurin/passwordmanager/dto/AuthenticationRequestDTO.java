package com.serhiihurin.passwordmanager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequestDTO {
    private String email;
    private String password;
    private String key;
}
