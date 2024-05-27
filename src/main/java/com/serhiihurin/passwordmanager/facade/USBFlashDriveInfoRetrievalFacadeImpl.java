package com.serhiihurin.passwordmanager.facade;

import com.serhiihurin.passwordmanager.entity.User;
import com.serhiihurin.passwordmanager.facade.interfaces.USBFlashDriveInfoRetrievalFacade;
import com.serhiihurin.passwordmanager.service.interfaces.USBFlashDriveInfoRetrievalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class USBFlashDriveInfoRetrievalFacadeImpl implements USBFlashDriveInfoRetrievalFacade {
    private final USBFlashDriveInfoRetrievalService usbService;

    @Override
    public String linkUSBFlashDrive(User currentAuthenticatedUser) {
        Callable<String> usbFlashDriveTask = () -> {
            String USBFlashDriveInfo;
            do {
                USBFlashDriveInfo = usbService.getUSBFlashDriveInfo();
                if (!Objects.equals(USBFlashDriveInfo, "")) {
                    log.info("Got string: {}", USBFlashDriveInfo);
                    return USBFlashDriveInfo;
                } else {
                    try {
                        log.info("Empty data");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } while (Objects.equals(USBFlashDriveInfo, ""));
            return null; // Якщо нічого не знайдено, повертаємо null
        };

        String USBFlashDriveInfo = null;
        try {
            USBFlashDriveInfo = usbFlashDriveTask.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return USBFlashDriveInfo;
//        CompletableFuture<String> future = new CompletableFuture<>();
//
//        Thread thread = new Thread(() -> {
//            String userMasterPassword;
//            do {
//                userMasterPassword = usbService.getUSBFlashDriveInfo();
//                if (!Objects.equals(userMasterPassword, "")) {
//                    future.complete(userMasterPassword);
//                    log.info("Отримано рядок: " + userMasterPassword);
//                } else {
//                    try {
//                        log.info("Empty data");
//                        Thread.sleep(2000); // Почекати 2 секунди
//                    } catch (InterruptedException e) {
//                        future.completeExceptionally(e);
//                    }
//                }
//            } while (Objects.equals(userMasterPassword, ""));
//        });
//
//        thread.start();
//        return future;
    }

    @Override
    public void unlinkUSBFlashDrive(User currentAuthenticatedUser) {

    }
}
