package com.serhiihurin.passwordmanager.facade;

import com.serhiihurin.passwordmanager.dto.RecordExtendedViewDTO;
import com.serhiihurin.passwordmanager.dto.RecordSimpleViewDTO;
import com.serhiihurin.passwordmanager.entity.Group;
import com.serhiihurin.passwordmanager.entity.Record;
import com.serhiihurin.passwordmanager.entity.User;
import com.serhiihurin.passwordmanager.enums.EntityType;
import com.serhiihurin.passwordmanager.facade.interfaces.RecordFacade;
import com.serhiihurin.passwordmanager.service.interfaces.EncryptionService;
import com.serhiihurin.passwordmanager.service.interfaces.GeneratorService;
import com.serhiihurin.passwordmanager.service.interfaces.GroupService;
import com.serhiihurin.passwordmanager.service.interfaces.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class RecordFacadeImpl implements RecordFacade {
    private final RecordService recordService;
    private final GroupService groupService;
    private final GeneratorService generatorService;
    private final EncryptionService encryptionService;
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
    public List<Record> getAllRecordsByUserIdAndGroupId(User currentAuthenticatedUser, String groupId) {
        return null;
    }

    @Override
    public RecordSimpleViewDTO getRecordByUserId(User currentAuthenticatedUser, String recordId) {
        return modelMapper.map(
                recordService.getRecordByUserId(currentAuthenticatedUser.getUserId(), recordId),
                RecordSimpleViewDTO.class
        );
    }

    @Override
    public RecordExtendedViewDTO getExtendedRecordByUserId(User currentAuthenticatedUser, String recordId) {
        RecordExtendedViewDTO recordExtendedViewDTO = modelMapper.map(
                recordService.getRecordByUserId(currentAuthenticatedUser.getUserId(), recordId),
                RecordExtendedViewDTO.class
        );
        recordExtendedViewDTO.setUsername(
                encryptionService.decrypt(recordExtendedViewDTO.getUsername(), Optional.empty())
        );
        recordExtendedViewDTO.setPassword(
                encryptionService.decrypt(recordExtendedViewDTO.getPassword(), Optional.empty())
        );
        return recordExtendedViewDTO;
    }

    @Override
    public Record getFullRecordInfoByUserId(User currentAuthenticatedUser, String recordId) {
        return recordService.getRecordByUserId(currentAuthenticatedUser.getUserId(), recordId);
    }


    @Override
    public List<RecordSimpleViewDTO> filterRecordsByTitle(String userId, String filterText) {
        return modelMapper.map(
                recordService.filterRecordsByTitle(userId, filterText),
                new TypeToken<List<RecordSimpleViewDTO>>(){
                }.getType()
        );
    }

    @Override
    public void createRecord(User currentAuthenticatedUser, RecordExtendedViewDTO recordExtendedViewDTO) {
        if (recordExtendedViewDTO.getRecordId() == null){
            recordExtendedViewDTO.setRecordId(generatorService.generateEntityId(EntityType.RECORD));
        }
        if (recordExtendedViewDTO.getGroup() != null) {
            Group group = recordExtendedViewDTO.getGroup();
            group.setCapacity(group.getCapacity() + 1);
            groupService.saveGroup(group);
        }
        recordExtendedViewDTO.setUsername(
                encryptionService.encrypt(recordExtendedViewDTO.getUsername(), Optional.empty())
        );
        recordExtendedViewDTO.setPassword(
                encryptionService.encrypt(recordExtendedViewDTO.getPassword(), Optional.empty())
        );
        recordService.createRecord(currentAuthenticatedUser, recordExtendedViewDTO);
    }

    @Override
    public void deleteRecord(User currentAuthenticatedUser, String recordId) {
        recordService.deleteRecord(recordId);
    }
}
