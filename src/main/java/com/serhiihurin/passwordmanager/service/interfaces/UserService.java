package com.serhiihurin.passwordmanager.service.interfaces;

import com.serhiihurin.passwordmanager.entity.User;

public interface UserService {
    User getUser(Long userId);
}
