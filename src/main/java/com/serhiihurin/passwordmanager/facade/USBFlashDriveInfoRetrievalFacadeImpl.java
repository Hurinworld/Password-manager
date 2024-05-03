package com.serhiihurin.passwordmanager.facade;

import com.serhiihurin.passwordmanager.entity.User;
import com.serhiihurin.passwordmanager.facade.interfaces.USBFlashDriveInfoRetrievalFacade;
import com.serhiihurin.passwordmanager.service.interfaces.USBFlashDriveInfoRetrievalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@Slf4j
public class USBFlashDriveInfoRetrievalFacadeImpl implements USBFlashDriveInfoRetrievalFacade {
    private final USBFlashDriveInfoRetrievalService usbService;

    @Override
    public void linkUSBFlashDrive(User currentAuthenticatedUser) {
        Thread thread = new Thread(() -> {
            // Поки метод не поверне значення відмінне від null, викликаємо його кожні 2 секунди
            String userMasterPassword;
            do {
                userMasterPassword = usbService.getUSBFlashDriveInfo(); // Викликаємо метод, що повертає рядок
                if (!Objects.equals(userMasterPassword, "")) {
                    log.info("Отримано рядок: " + userMasterPassword);

                } else {
                    try {
                        log.info("Empty data");
                        Thread.sleep(2000); // Почекати 2 секунди
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } while (Objects.equals(userMasterPassword, ""));
        });

        // Запускаємо потік
        thread.start();
    }

    @Override
    public void unlinkUSBFlashDrive(User currentAuthenticatedUser) {

    }
}
