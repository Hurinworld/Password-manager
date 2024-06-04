package com.serhiihurin.passwordmanager.service.interfaces;

public interface FileService {
    void  generateKeyFile();
    String getKeyFromFile();
}
