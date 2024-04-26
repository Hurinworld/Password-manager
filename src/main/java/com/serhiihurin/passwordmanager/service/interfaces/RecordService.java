package com.serhiihurin.passwordmanager.service.interfaces;

import com.serhiihurin.passwordmanager.dto.RecordExtendedViewDTO;
import com.serhiihurin.passwordmanager.entity.Record;
import com.serhiihurin.passwordmanager.entity.User;

import java.util.List;

public interface RecordService {
    List<Record> getAllRecordsByUserId(String userId);
    List<Record> getAllRecordsByUserIdAndGroupId(String userId, String groupId);
    List<Record> filterRecordsByTitle(String userId, String filterText);
    Record getRecordByUserId(String userId, String recordId);
    Record createRecord(User currentAuthenticatedUser, RecordExtendedViewDTO recordExtendedViewDTO);
    void deleteRecord(String recordId);
}
