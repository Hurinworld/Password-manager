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
import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {
    private final USBDeviceDetectorManager usbDeviceDetectorManager;
    private final GeneratorService generatorService;
    private final String CONTENT = getContent();
    private final String FILEPATH = getFilepath();

    @Override
    public void generateKeyFile() {
        String filePath = usbDeviceDetectorManager.getRemovableDevices().get(0).getDevice() + "USBKey.key";
        File file = new File(filePath);
//        // Ensure the parent directories exist
//        file.getParentFile().mkdirs();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(Objects.requireNonNull(CONTENT));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getKeyFromFile() {
        String key;
        try {
            byte[] keyBytes = Files.readAllBytes(Path.of(Objects.requireNonNull(FILEPATH)));
            key = Arrays.toString(keyBytes);
            return key;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getContent(){
        if (generatorService != null) {
            return generatorService.generatePassword("100", true, true, true);
        } else return null;
    }

    private String getFilepath() {
        if (usbDeviceDetectorManager != null) {
            return usbDeviceDetectorManager.getRemovableDevices().get(0).getDevice() + "USBKey.key";
        } else return null;
    }
}
