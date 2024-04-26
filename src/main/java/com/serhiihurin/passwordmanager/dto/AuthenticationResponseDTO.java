package com.serhiihurin.passwordmanager.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponseDTO {
    String accessToken;
}
