package com.serhiihurin.passwordmanager.facade;

import com.serhiihurin.passwordmanager.entity.Record;
import com.serhiihurin.passwordmanager.entity.User;
import com.serhiihurin.passwordmanager.enums.ExistingRecordsOperationType;
import com.serhiihurin.passwordmanager.facade.interfaces.USBFlashDriveInfoRetrievalFacade;
import com.serhiihurin.passwordmanager.service.interfaces.EncryptionService;
import com.serhiihurin.passwordmanager.service.interfaces.FileService;
import com.serhiihurin.passwordmanager.service.interfaces.RecordService;
import com.serhiihurin.passwordmanager.service.interfaces.USBFlashDriveInfoRetrievalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.samuelcampos.usbdrivedetector.USBDeviceDetectorManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

@Component
@RequiredArgsConstructor
@Slf4j
public class USBFlashDriveInfoRetrievalFacadeImpl implements USBFlashDriveInfoRetrievalFacade {
    private final USBFlashDriveInfoRetrievalService usbService;
    private final USBDeviceDetectorManager usbDeviceDetectorManager;
    private final FileService fileService;
    private final EncryptionService encryptionService;
    private final RecordService recordService;

    @Override
    public String linkUSBFlashDrive(boolean isRegisterProcess) {
        Callable<String> usbFlashDriveTask = () -> {
            String USBFlashDriveInfo;
            String usbUUID;
            String USBKey;
            do {
                USBFlashDriveInfo = usbService.getUSBFlashDriveInfo();
                usbUUID = usbDeviceDetectorManager.getRemovableDevices().get(0).getUuid();
                if (isRegisterProcess) {
                    fileService.generateKeyFile();
                }
                USBKey = fileService.getKeyFromFile();
                if (!Objects.equals(USBFlashDriveInfo, "")) {
                    log.info("Got string: {}", USBFlashDriveInfo);
                    log.info(usbDeviceDetectorManager.getRemovableDevices().get(0).toString());
                    return USBFlashDriveInfo + usbUUID + USBKey;
                } else {
                    try {
                        log.info("Empty data");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage());
                    }
                }
            } while (Objects.equals(USBFlashDriveInfo, ""));
            return null;
        };

        String USBFlashDriveInfo = null;
        try {
            USBFlashDriveInfo = usbFlashDriveTask.call();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return USBFlashDriveInfo;
    }

    @Override
    public void changeUSBFlashDrive(User currentAuthenticatedUser, ExistingRecordsOperationType operationType) {
        List<Record> recordList = recordService.getAllRecordsByUserId(currentAuthenticatedUser.getUserId());
        for(Record record : recordList) {
            if (operationType == ExistingRecordsOperationType.DECRYPT) {
                record.setUsername(encryptionService.decrypt(record.getUsername()));
                record.setPassword(encryptionService.decrypt(record.getPassword()));
            }
            if (operationType == ExistingRecordsOperationType.ENCRYPT) {
                record.setUsername(encryptionService.encrypt(record.getUsername()));
                record.setPassword(encryptionService.encrypt(record.getPassword()));
            }
            recordService.updateRecord(record);
        }
    }
}
