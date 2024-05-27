package com.serhiihurin.passwordmanager.facade;

import com.serhiihurin.passwordmanager.dto.AuthenticationRequestDTO;
import com.serhiihurin.passwordmanager.dto.AuthenticationResponseDTO;
import com.serhiihurin.passwordmanager.dto.RegisterRequestDTO;
import com.serhiihurin.passwordmanager.entity.User;
import com.serhiihurin.passwordmanager.facade.interfaces.AuthenticationFacade;
import com.serhiihurin.passwordmanager.service.interfaces.AuthenticationService;
import com.vaadin.flow.component.notification.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFacadeImpl implements AuthenticationFacade {
    private final AuthenticationService authenticationService;
    @Override
    public AuthenticationResponseDTO registerUser(
            String firstName,
            String lastName,
            String email,
            String masterPassword
    ) {
        if (firstName.trim().isEmpty()) {
            Notification.show("First name field is required");
        } else if (lastName.trim().isEmpty()) {
            Notification.show("Last name field is required");
        } else if (email.trim().isEmpty()) {
            Notification.show("Email field is required");
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
        log.info(
                "Triggered authentication method for user: {} with password: {}",
                request.getEmail(),
                request.getPassword()
        );
        return authenticationService.authenticate(request);
    }

    @Override
    public User getAuthenticatedUser() {
        return authenticationService.getAuthenticatedUser();
    }

    @Override
    public void logout() {
        authenticationService.logout();
    }
}
