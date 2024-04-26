package com.serhiihurin.passwordmanager.controller_advice;

import com.serhiihurin.passwordmanager.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.context.request.WebRequest;

import java.security.Principal;

@ControllerAdvice
public class UserControllerAdvice {
    @ModelAttribute
    public User getCurrentUser(WebRequest request, Principal principal) {
        if (request == null || !(principal instanceof Authentication authentication)) {
            return null;
        }

        return (User) authentication.getPrincipal();
    }
}
