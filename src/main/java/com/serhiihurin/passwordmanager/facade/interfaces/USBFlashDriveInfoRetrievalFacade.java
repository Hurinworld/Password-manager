package com.serhiihurin.passwordmanager.facade.interfaces;

import com.serhiihurin.passwordmanager.entity.User;

public interface USBFlashDriveInfoRetrievalFacade {
    String linkUSBFlashDrive(boolean isRegisterProcess);
    void unlinkUSBFlashDrive(User currentAuthenticatedUser);
}
