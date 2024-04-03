package com.serhiihurin.passwordmanager.service;

import com.serhiihurin.passwordmanager.dao.RecordRepository;
import com.serhiihurin.passwordmanager.entity.Record;
import com.serhiihurin.passwordmanager.service.interfaces.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecordServiceImpl implements RecordService {
    private final RecordRepository recordRepository;

    @Override
    public List<Record> getAllRecordsByUserId(Long userId) {
        return recordRepository.getRecordsByUserUserId(userId);
    }

    @Override
    public List<Record> getAllRecordsByUserIdAndGroupId(Long userId, Long groupId) {
        return null;
    }

    @Override
    public List<Record> filterRecordsByTitle(Long userId, String filterText) {
        if (filterText == null || filterText.isEmpty()) {
            return recordRepository.getRecordsByUserUserId(userId);
        } else {
            return recordRepository.search(userId, filterText);
        }
    }

    @Override
    public Record getRecordByUserId(Long userId, Long recordId) {
        return recordRepository.getRecordByUserUserIdAndRecordId(userId, recordId);
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
