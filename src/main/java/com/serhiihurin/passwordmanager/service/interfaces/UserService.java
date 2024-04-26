package com.serhiihurin.passwordmanager.service.interfaces;

import com.serhiihurin.passwordmanager.dto.RegisterRequestDTO;
import com.serhiihurin.passwordmanager.entity.User;

public interface UserService {
    User getUser(String userId);
    User getUserByEmail(String email);
    User createUser(RegisterRequestDTO registerRequestDTO);
}
