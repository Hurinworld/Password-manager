package com.serhiihurin.passwordmanager.service.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;

public interface JWTService {
    String extractUsername(String token);

    String extractToken(HttpServletRequest request);

    String generateAccessToken(Map<String, Object> extraClaims,
                               UserDetails userDetails);

    String generateAccessToken(UserDetails userDetails);

    boolean isTokenValid(String token);
}