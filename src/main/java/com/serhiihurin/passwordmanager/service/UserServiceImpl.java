package com.serhiihurin.passwordmanager.service;

import com.serhiihurin.passwordmanager.dao.UserRepository;
import com.serhiihurin.passwordmanager.dto.RegisterRequestDTO;
import com.serhiihurin.passwordmanager.entity.User;
import com.serhiihurin.passwordmanager.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Oops!"));
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.getByEmail(email)
                .orElseThrow(() -> new RuntimeException("Could not find user"));
    }

    @Override
    public User createUser(RegisterRequestDTO registerRequestDTO) {
        User user = User.builder()
                .userId(1L)
                .firstName(registerRequestDTO.getFirstName())
                .lastName(registerRequestDTO.getLastName())
                .email(registerRequestDTO.getEmail())
                .masterPassword(passwordEncoder.encode(registerRequestDTO.getMasterPassword()))
                .build();
        return userRepository.save(user);
    }
}
