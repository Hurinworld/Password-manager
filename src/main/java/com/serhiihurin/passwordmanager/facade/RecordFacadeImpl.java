package com.serhiihurin.passwordmanager.facade;

import com.serhiihurin.passwordmanager.dto.RecordCreationRequestDTO;
import com.serhiihurin.passwordmanager.dto.RecordSimpleViewDTO;
import com.serhiihurin.passwordmanager.entity.Record;
import com.serhiihurin.passwordmanager.entity.User;
import com.serhiihurin.passwordmanager.facade.interfaces.RecordFacade;
import com.serhiihurin.passwordmanager.service.interfaces.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecordFacadeImpl implements RecordFacade {
    private final RecordService recordService;
    private final ModelMapper modelMapper;

    @Override
    public List<Record> getAllRecordsByUserId(User currentAuthenticatedUser) {
        return null;
    }

    @Override
    public List<Record> getAllRecordsByUserIdAndGroupId(User currentAuthenticatedUser, Long groupId) {
        return null;
    }

    @Override
    public RecordSimpleViewDTO getRecordByUserId(User currentAuthenticatedUser, Long recordId) {
        return modelMapper.map(
                recordService.getRecordByUserId(currentAuthenticatedUser.getUserId(), recordId),
                RecordSimpleViewDTO.class
        );
    }

    @Override
    public Record createRecord(User currentAuthenticatedUser, RecordCreationRequestDTO recordCreationRequestDTO) {
        return null;
    }

    @Override
    public void deleteRecord(User currentAuthenticatedUser, Long recordId) {

    }
}
