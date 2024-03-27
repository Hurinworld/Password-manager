package com.serhiihurin.passwordmanager.service.interfaces;

import com.serhiihurin.passwordmanager.entity.Record;

import java.util.List;

public interface RecordService {
    List<Record> getAllRecordsByUserId(Long userId);
    List<Record> getAllRecordsByUserIdAndGroupId(Long userId, Long groupId);
    Record getRecordByUserId(Long UserId, Long recordId);
    Record createRecord();
    Record deleteRecord(Long recordId);
}
