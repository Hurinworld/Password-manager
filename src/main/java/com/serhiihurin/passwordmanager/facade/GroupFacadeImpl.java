package com.serhiihurin.passwordmanager.facade;

import com.serhiihurin.passwordmanager.entity.Group;
import com.serhiihurin.passwordmanager.entity.Record;
import com.serhiihurin.passwordmanager.entity.User;
import com.serhiihurin.passwordmanager.enums.EntityType;
import com.serhiihurin.passwordmanager.facade.interfaces.GroupFacade;
import com.serhiihurin.passwordmanager.service.interfaces.GeneratorService;
import com.serhiihurin.passwordmanager.service.interfaces.GroupService;
import com.serhiihurin.passwordmanager.service.interfaces.RecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class GroupFacadeImpl implements GroupFacade {
    private final GroupService groupService;
    private final RecordService recordService;
    private final GeneratorService generatorService;


    @Override
    public List<Group> getGroupsByUserId(User currentAuthenticatedUser) {
        return groupService.getGroupsByUserId(currentAuthenticatedUser.getUserId());
    }

    @Override
    public void createGroup(Group group) {
        if (group.getGroupId() == null) {
            group.setGroupId(
                    generatorService.generateEntityId(EntityType.GROUP)
            );
        }
        groupService.saveGroup(group);
        List<Record> records = group.getRecords();
        records.forEach(record -> {
            record.setGroup(group);
            recordService.updateRecord(record);
        });
    }

    @Override
    public void deleteGroup(String groupId) {
        Group group = groupService.getGroup(groupId);
        for (Record record : group.getRecords()) {
            record.setGroup(null);
            recordService.updateRecord(record);
        }
        groupService.deleteGroup(groupId);
    }
}
