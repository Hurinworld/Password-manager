package com.serhiihurin.passwordmanager.facade.interfaces;

import com.serhiihurin.passwordmanager.entity.User;
import com.serhiihurin.passwordmanager.enums.ExistingRecordsOperationType;

public interface USBFlashDriveInfoRetrievalFacade {
    String linkUSBFlashDrive(boolean isRegisterProcess);
    void changeUSBFlashDrive(User currentUser, ExistingRecordsOperationType operationType);
}
