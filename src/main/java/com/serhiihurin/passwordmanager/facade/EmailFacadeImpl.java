package com.serhiihurin.passwordmanager.facade;

import com.serhiihurin.passwordmanager.facade.interfaces.EmailFacade;
import com.serhiihurin.passwordmanager.service.interfaces.EmailService;
import com.serhiihurin.passwordmanager.service.interfaces.GeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailFacadeImpl implements EmailFacade {
    private final EmailService emailService;
    private final GeneratorService generatorService;


    @Override
    public void sendGreetingsEmail(String toEmail, String name) {
        emailService.sendGreetingsEmail(toEmail,name);
    }

    @Override
    public String sendPasswordChangingVerificationCode(String toEmail) {
        String verificationCode = generatorService.generatePassword(
                "8",
                true,
                true,
                false
        );
        emailService.sendPasswordChangingVerificationCode(toEmail, verificationCode);
        return verificationCode;
    }

    @Override
    public void sendEmailChangingVerificationCode(String toEmail) {

    }
}
