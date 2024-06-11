package com.serhiihurin.passwordmanager.service;

import com.serhiihurin.passwordmanager.dao.UserRepository;
import com.serhiihurin.passwordmanager.dto.RegisterRequestDTO;
import com.serhiihurin.passwordmanager.entity.User;
import com.serhiihurin.passwordmanager.enums.EntityType;
import com.serhiihurin.passwordmanager.service.interfaces.GeneratorService;
import com.serhiihurin.passwordmanager.service.interfaces.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final GeneratorService generatorService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUser(String userId) {
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
        String masterPassword = registerRequestDTO.getMasterPassword();
        log.info("Initial password {}", masterPassword);
        User user = User.builder()
                .userId(generatorService.generateEntityId(EntityType.USER))
                .firstName(registerRequestDTO.getFirstName())
                .lastName(registerRequestDTO.getLastName())
                .email(registerRequestDTO.getEmail())
                .masterPassword(passwordEncoder.encode(registerRequestDTO.getMasterPassword()))
                .build();
        return userRepository.save(user);
    }

    @Override
    public void saveUser(User user) {
        if (!user.getMasterPassword().startsWith("$2a$10$")) {
            user.setMasterPassword(passwordEncoder.encode(user.getMasterPassword()));
        }
        userRepository.save(user);
    }
}
