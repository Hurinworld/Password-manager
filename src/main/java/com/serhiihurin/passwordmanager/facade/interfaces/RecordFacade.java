package com.serhiihurin.passwordmanager.facade.interfaces;

import com.serhiihurin.passwordmanager.dto.RecordCreationRequestDTO;
import com.serhiihurin.passwordmanager.dto.RecordExtendedViewDTO;
import com.serhiihurin.passwordmanager.dto.RecordSimpleViewDTO;
import com.serhiihurin.passwordmanager.entity.Record;
import com.serhiihurin.passwordmanager.entity.User;

import java.util.List;

public interface RecordFacade {
    List<RecordSimpleViewDTO> getAllRecordsByUserId(User currentAuthenticatedUser);
    List<Record> getAllRecordsByUserIdAndGroupId(User currentAuthenticatedUser, Long groupId);
    RecordSimpleViewDTO getRecordByUserId(User currentAuthenticatedUser, Long recordId);
    RecordExtendedViewDTO getExtendedRecordByUserId(User currentAuthenticatedUser, Long recordId);
    List<RecordSimpleViewDTO> filterRecordsByTitle(Long userId, String filterText);
    void createRecord(User currentAuthenticatedUser, RecordExtendedViewDTO recordExtendedViewDTO);
    void deleteRecord(User currentAuthenticatedUser, Long recordId);
}
