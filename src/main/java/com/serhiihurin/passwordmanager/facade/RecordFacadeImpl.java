package com.serhiihurin.passwordmanager.facade;

import com.serhiihurin.passwordmanager.dto.RecordExtendedViewDTO;
import com.serhiihurin.passwordmanager.dto.RecordSimpleViewDTO;
import com.serhiihurin.passwordmanager.entity.Record;
import com.serhiihurin.passwordmanager.entity.User;
import com.serhiihurin.passwordmanager.facade.interfaces.RecordFacade;
import com.serhiihurin.passwordmanager.service.interfaces.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecordFacadeImpl implements RecordFacade {
    private final RecordService recordService;
    private final ModelMapper modelMapper;

    @Override
    public List<RecordSimpleViewDTO> getAllRecordsByUserId(User currentAuthenticatedUser) {
        return modelMapper.map(
                recordService.getAllRecordsByUserId(currentAuthenticatedUser.getUserId()),
                new TypeToken<List<RecordSimpleViewDTO>>(){
                }.getType()
        );
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
    public RecordExtendedViewDTO getExtendedRecordByUserId(User currentAuthenticatedUser, Long recordId) {
        return modelMapper.map(
                recordService.getRecordByUserId(currentAuthenticatedUser.getUserId(), recordId),
                RecordExtendedViewDTO.class
        );
    }

    @Override
    public List<RecordSimpleViewDTO> filterRecordsByTitle(Long userId, String filterText) {
        return modelMapper.map(
                recordService.filterRecordsByTitle(userId, filterText),
                new TypeToken<List<RecordSimpleViewDTO>>(){
                }.getType()
        );
    }

    @Override
    public void createRecord(User currentAuthenticatedUser, RecordExtendedViewDTO recordExtendedViewDTO) {
        recordExtendedViewDTO.setRecordId(1L);
        recordService.createRecord(currentAuthenticatedUser, recordExtendedViewDTO);
    }

    @Override
    public void deleteRecord(User currentAuthenticatedUser, Long recordId) {
        recordService.deleteRecord(recordId);
    }
}
