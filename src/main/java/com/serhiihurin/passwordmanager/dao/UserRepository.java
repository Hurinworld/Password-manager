package com.serhiihurin.passwordmanager.dao;

import com.serhiihurin.passwordmanager.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getByEmail(String email);
}
