package com.serhiihurin.passwordmanager.facade.interfaces;

import com.serhiihurin.passwordmanager.entity.User;

public interface USBFlashDriveInfoRetrievalFacade {
    String linkUSBFlashDrive(User currentAuthenticatedUser);
    void unlinkUSBFlashDrive(User currentAuthenticatedUser);
}
