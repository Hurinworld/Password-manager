package com.serhiihurin.passwordmanager.facade.interfaces;

import com.serhiihurin.passwordmanager.dto.RecordCreationRequestDTO;
import com.serhiihurin.passwordmanager.entity.Record;
import com.serhiihurin.passwordmanager.entity.User;

import java.util.List;

public interface RecordFacade {
    List<Record> getAllRecordsByUserId(User currentAuthenticatedUser);
    List<Record> getAllRecordsByUserIdAndGroupId(User currentAuthenticatedUser, Long groupId);
    Record getRecordByUserId(User currentAuthenticatedUser, Long recordId);
    Record createRecord(User currentAuthenticatedUser, RecordCreationRequestDTO recordCreationRequestDTO);
    void deleteRecord(User currentAuthenticatedUser, Long recordId);
}
