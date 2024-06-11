package com.serhiihurin.passwordmanager.service;

import com.serhiihurin.passwordmanager.service.interfaces.EmailService;
import com.serhiihurin.passwordmanager.service.interfaces.GeneratorService;
import com.serhiihurin.passwordmanager.service.interfaces.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    private final UserService userService;
    private final GeneratorService generatorService;
    private final TemplateEngine templateEngine;
    private final Context context;

    @Async
    @Override
    public void sendGreetingsEmail(String toEmail, String name) {
        context.setVariable("name", name);

        buildAndSendMessage("greetings-email", toEmail, "Welcome!");

        log.info("sent email to {}", toEmail);
    }

    @Async
    @Override
    public void sendPasswordChangingVerificationCode(String toEmail, String verificationCode) {
        context.setVariable("name", userService.getUserByEmail(toEmail).getFirstName());
        context.setVariable("verificationCode", verificationCode);

        buildAndSendMessage("password-changing-verification-email",
                toEmail, "Password changing verification");

        log.info("sent password changing verification code to {}", toEmail);
    }

    @Override
    public void sendEmailChangingVerificationCode(String toEmail) {

    }

    private void buildAndSendMessage(
            String templateName,
            String toEmail,
            String subject
    ) {
        String emailContent = templateEngine.process(templateName, context);
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("password.manager@gmail.com");
            helper.setTo(toEmail);
            helper.setSubject(subject);
            helper.setText(emailContent, true);
            context.clearVariables();
        } catch (MessagingException messagingException) {
            throw new RuntimeException(messagingException);
        }

        javaMailSender.send(message);
    }
}
