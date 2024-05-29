package com.serhiihurin.passwordmanager.facade.interfaces;

import com.serhiihurin.passwordmanager.dto.AuthenticationRequestDTO;
import com.serhiihurin.passwordmanager.dto.AuthenticationResponseDTO;
import com.serhiihurin.passwordmanager.dto.RegisterRequestDTO;
import com.serhiihurin.passwordmanager.entity.User;

public interface AuthenticationFacade {
    AuthenticationResponseDTO registerUser(
            String firstName,
            String lastName,
            String email,
            String masterPassword
    );
    AuthenticationResponseDTO authenticateUser(AuthenticationRequestDTO request);
    User getAuthenticatedUser();
    void logout();
}
