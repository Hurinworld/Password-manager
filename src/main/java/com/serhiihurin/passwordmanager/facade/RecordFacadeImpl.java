package com.serhiihurin.passwordmanager.facade;

import com.serhiihurin.passwordmanager.dto.RecordCreationRequestDTO;
import com.serhiihurin.passwordmanager.entity.Record;
import com.serhiihurin.passwordmanager.entity.User;
import com.serhiihurin.passwordmanager.facade.interfaces.RecordFacade;

import java.util.List;

public class RecordFacadeImpl implements RecordFacade {

    @Override
    public List<Record> getAllRecordsByUserId(User currentAuthenticatedUser) {
        return null;
    }

    @Override
    public List<Record> getAllRecordsByUserIdAndGroupId(User currentAuthenticatedUser, Long groupId) {
        return null;
    }

    @Override
    public Record getRecordByUserId(User currentAuthenticatedUser, Long recordId) {
        return null;
    }

    @Override
    public Record createRecord(User currentAuthenticatedUser, RecordCreationRequestDTO recordCreationRequestDTO) {
        return null;
    }

    @Override
    public void deleteRecord(User currentAuthenticatedUser, Long recordId) {

    }
}
