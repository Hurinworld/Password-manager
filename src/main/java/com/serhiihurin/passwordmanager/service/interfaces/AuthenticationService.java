package com.serhiihurin.passwordmanager.service.interfaces;

import com.serhiihurin.passwordmanager.dto.AuthenticationRequestDTO;
import com.serhiihurin.passwordmanager.dto.AuthenticationResponseDTO;
import com.serhiihurin.passwordmanager.dto.RegisterRequestDTO;
import com.serhiihurin.passwordmanager.entity.User;

public interface AuthenticationService {
    AuthenticationResponseDTO register(RegisterRequestDTO request);

    AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request);

    User getAuthenticatedUser();

    void logout();
}
