package com.serhiihurin.passwordmanager.facade;

import com.serhiihurin.passwordmanager.entity.User;
import com.serhiihurin.passwordmanager.facade.interfaces.USBFlashDriveInfoRetrievalFacade;
import com.serhiihurin.passwordmanager.service.interfaces.USBFlashDriveInfoRetrievalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class USBFlashDriveInfoRetrievalFacadeImpl implements USBFlashDriveInfoRetrievalFacade {
    private final USBFlashDriveInfoRetrievalService usbService;

    @Override
    public void linkUSBFlashDrive(User currentAuthenticatedUser) {
        String userMasterPassword = usbService.getUSBFlashDriveInfo();
    }

    @Override
    public void unlinkUSBFlashDrive(User currentAuthenticatedUser) {

    }
}
