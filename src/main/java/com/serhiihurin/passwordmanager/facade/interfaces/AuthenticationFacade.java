package com.serhiihurin.passwordmanager.facade.interfaces;

import com.serhiihurin.passwordmanager.dto.AuthenticationRequestDTO;
import com.serhiihurin.passwordmanager.dto.AuthenticationResponseDTO;
import com.serhiihurin.passwordmanager.dto.RegisterRequestDTO;

public interface AuthenticationFacade {
    AuthenticationResponseDTO registerUser(
            String firstName,
            String lastName,
            String email,
            String masterPassword,
            String confirmMasterPassword
    );
    AuthenticationResponseDTO authenticateUser(AuthenticationRequestDTO request);
}
