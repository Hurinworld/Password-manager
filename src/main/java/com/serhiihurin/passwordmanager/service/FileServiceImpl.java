package com.serhiihurin.passwordmanager.service;

import com.serhiihurin.passwordmanager.service.interfaces.FileService;
import com.serhiihurin.passwordmanager.service.interfaces.GeneratorService;
import lombok.RequiredArgsConstructor;
import net.samuelcampos.usbdrivedetector.USBDeviceDetectorManager;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final USBDeviceDetectorManager usbDeviceDetectorManager;
    private final GeneratorService generatorService;

    @Override
    public void generateKeyFile() {
        String filePath = getFilepath();
        if (filePath == null) {
            throw new RuntimeException("USB device not found or not accessible");
        }
        File file = new File(filePath);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(Objects.requireNonNull(getContent()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getKeyFromFile() {
        String filePath = getFilepath();
        if (filePath == null) {
            throw new RuntimeException("USB device not found or not accessible");
        }

        try {
            byte[] keyBytes = Files.readAllBytes(Path.of(filePath));
            return new String(keyBytes);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getContent() {
        return generatorService.generatePassword("100", true, true, true);
    }

    private String getFilepath() {
        if (usbDeviceDetectorManager != null && !usbDeviceDetectorManager.getRemovableDevices().isEmpty()) {
            return usbDeviceDetectorManager.getRemovableDevices().get(0).getDevice() + "USBKey.key";
        } else {
            return null;
        }
    }
}

