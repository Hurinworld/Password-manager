package com.serhiihurin.passwordmanager.service;

import com.serhiihurin.passwordmanager.dto.AuthenticationRequestDTO;
import com.serhiihurin.passwordmanager.dto.AuthenticationResponseDTO;
import com.serhiihurin.passwordmanager.dto.RegisterRequestDTO;
import com.serhiihurin.passwordmanager.entity.User;
import com.serhiihurin.passwordmanager.service.interfaces.AuthenticationService;
import com.serhiihurin.passwordmanager.service.interfaces.JWTService;
import com.serhiihurin.passwordmanager.service.interfaces.UserService;
import com.vaadin.flow.spring.security.AuthenticationContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    private final transient AuthenticationContext authenticationContext;

    @Override
    public AuthenticationResponseDTO register(RegisterRequestDTO registerRequestDTO) {
        User user = userService.createUser(registerRequestDTO);
        String jwtToken = jwtService.generateAccessToken(user);
        return AuthenticationResponseDTO.builder()
                .accessToken(jwtToken)
                .build();
    }

    @Override
    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getEmail(),
                                request.getPassword()
                        )
                )
        );
        User user = userService.getUserByEmail(request.getEmail());
        String jwtToken = jwtService.generateAccessToken(user);
        return AuthenticationResponseDTO.builder()
                .accessToken(jwtToken)
                .build();
    }

    @Override
    public User getAuthenticatedUser() {
        return authenticationContext.getAuthenticatedUser(User.class)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public void logout() {
        authenticationContext.logout();
    }
}
