package com.serhiihurin.passwordmanager.facade.interfaces;

import com.serhiihurin.passwordmanager.dto.RecordExtendedViewDTO;
import com.serhiihurin.passwordmanager.dto.RecordSimpleViewDTO;
import com.serhiihurin.passwordmanager.entity.Record;
import com.serhiihurin.passwordmanager.entity.User;

import java.util.List;

public interface RecordFacade {
    List<RecordSimpleViewDTO> getAllRecordsByUserId(User currentAuthenticatedUser);
    List<Record> getAllRecordsByUserIdAndGroupId(User currentAuthenticatedUser, String groupId);
    RecordSimpleViewDTO getRecordByUserId(User currentAuthenticatedUser, String recordId);
    RecordExtendedViewDTO getExtendedRecordByUserId(User currentAuthenticatedUser, String recordId);
    Record getFullRecordInfoByUserId(User currentAuthenticatedUser, String recordId);
    List<RecordSimpleViewDTO> filterRecordsByTitle(String userId, String filterText);
    void createRecord(User currentAuthenticatedUser, RecordExtendedViewDTO recordExtendedViewDTO);
    void deleteRecord(User currentAuthenticatedUser, String recordId);
}
