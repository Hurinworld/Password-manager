package com.serhiihurin.passwordmanager.service.interfaces;

import com.serhiihurin.passwordmanager.dto.AuthenticationRequestDTO;
import com.serhiihurin.passwordmanager.dto.AuthenticationResponseDTO;
import com.serhiihurin.passwordmanager.dto.RegisterRequestDTO;

public interface AuthenticationService {
    AuthenticationResponseDTO register(RegisterRequestDTO request);

    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);
}
