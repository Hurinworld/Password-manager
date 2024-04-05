package com.serhiihurin.passwordmanager.service.interfaces;

import com.serhiihurin.passwordmanager.dto.RecordExtendedViewDTO;
import com.serhiihurin.passwordmanager.entity.Record;
import com.serhiihurin.passwordmanager.entity.User;

import java.util.List;

public interface RecordService {
    List<Record> getAllRecordsByUserId(Long userId);
    List<Record> getAllRecordsByUserIdAndGroupId(Long userId, Long groupId);
    List<Record> filterRecordsByTitle(Long userId, String filterText);
    Record getRecordByUserId(Long userId, Long recordId);
    Record createRecord(User currentAuthenticatedUser, RecordExtendedViewDTO recordExtendedViewDTO);
    void deleteRecord(Long recordId);
}
