package com.serhiihurin.passwordmanager.service;

import com.serhiihurin.passwordmanager.connector.DllConnector;
import com.serhiihurin.passwordmanager.service.interfaces.USBFlashDriveInfoRetrievalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class USBFlashDriveInfoRetrievalServiceImpl implements USBFlashDriveInfoRetrievalService {
    private final DllConnector dllConnector;

    @Override
    public String getUSBFlashDriveInfo() {
        String rawUSBFlashDriveInfo = dllConnector.USBInfoExtractor();
        if (!(Objects.equals(rawUSBFlashDriveInfo, ""))) {
            String[] USBInfoArray = rawUSBFlashDriveInfo.replaceAll("DeviceID: ", "|")
                    .replaceAll("Model: ", "|")
                    .replaceAll("SerialNumber: ", "|")
                    .replaceAll("\n", "")
                    .split("\\|");
            String[] filteredInfoArray = {USBInfoArray[2], USBInfoArray[3]};
            return filteredInfoArray[0] + " " + filteredInfoArray[1];
        } else {
            return rawUSBFlashDriveInfo;
        }
    }
}
