package com.serhiihurin.passwordmanager.service;

import com.serhiihurin.passwordmanager.entity.Record;
import com.serhiihurin.passwordmanager.service.interfaces.RecordService;

import java.util.List;

public class RecordServiceImpl implements RecordService {

    @Override
    public List<Record> getAllRecordsByUserId(Long userId) {
        return null;
    }

    @Override
    public List<Record> getAllRecordsByUserIdAndGroupId(Long userId, Long groupId) {
        return null;
    }

    @Override
    public Record getRecordByUserId(Long UserId, Long recordId) {
        return null;
    }

    @Override
    public Record createRecord() {
        return null;
    }

    @Override
    public Record deleteRecord(Long recordId) {
        return null;
    }
}
