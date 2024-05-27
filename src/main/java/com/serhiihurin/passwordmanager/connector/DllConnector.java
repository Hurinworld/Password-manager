package com.serhiihurin.passwordmanager.connector;

public class DllConnector {
    public native String USBInfoExtractor();

    static {
        System.loadLibrary("USB Device Info Extractor");
    }
}
