package com.serhiihurin.passwordmanager.service;

import com.serhiihurin.passwordmanager.service.interfaces.USBFlashDriveInfoRetrievalService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class USBFlashDriveInfoRetrievalServiceImpl implements USBFlashDriveInfoRetrievalService {

    static {
        System.loadLibrary("USB-device-info-extractor");
    }

    private native String USBInfoExtractor();

    @Override
    public String getUSBFlashDriveInfo() {
        return USBInfoExtractor();
    }
}
