package com.serhiihurin.passwordmanager.facade;

import com.serhiihurin.passwordmanager.dto.AuthenticationRequestDTO;
import com.serhiihurin.passwordmanager.dto.AuthenticationResponseDTO;
import com.serhiihurin.passwordmanager.dto.RegisterRequestDTO;
import com.serhiihurin.passwordmanager.facade.interfaces.AuthenticationFacade;
import com.serhiihurin.passwordmanager.service.interfaces.AuthenticationService;
import com.vaadin.flow.component.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthenticationFacadeImpl implements AuthenticationFacade {
    private final AuthenticationService authenticationService;
    @Override
    public AuthenticationResponseDTO registerUser(
            String firstName,
            String lastName,
            String email,
            String masterPassword,
            String confirmMasterPassword
    ) {
        if (firstName.trim().isEmpty()) {
            Notification.show("First name field is required");
        } else if (lastName.trim().isEmpty()) {
            Notification.show("Last name field is required");
        } else if (email.trim().isEmpty()) {
            Notification.show("Email field is required");
        } else if (!masterPassword.equals(confirmMasterPassword)) {
            Notification.show("Passwords don't match");
        } else {
            AuthenticationResponseDTO authenticationResponseDTO = authenticationService.register(
                    RegisterRequestDTO.builder()
                            .firstName(firstName)
                            .lastName(lastName)
                            .email(email)
                            .masterPassword(masterPassword)
                            .build()
            );
            Notification.show("Registration successful!");
            return authenticationResponseDTO;
        }
        return null;
    }

    @Override
    public AuthenticationResponseDTO authenticateUser(AuthenticationRequestDTO request) {
        return authenticationService.authenticate(request);
    }
}
