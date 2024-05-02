package com.serhiihurin.passwordmanager.scheduler;

import com.serhiihurin.passwordmanager.service.interfaces.USBFlashDriveInfoRetrievalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class Scheduler {
//    private final USBFlashDriveInfoRetrievalService usbService;

//    @Async
//    @Scheduled(fixedRate = 2000)
//    public void getUSBFlashDriveInfo() {
//        log.info("Attempt to get USD flash drive info...");
//    }
}
