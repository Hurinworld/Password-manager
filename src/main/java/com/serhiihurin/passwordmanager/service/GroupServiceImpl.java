package com.serhiihurin.passwordmanager.service;

import com.serhiihurin.passwordmanager.dao.GroupRepository;
import com.serhiihurin.passwordmanager.entity.Group;
import com.serhiihurin.passwordmanager.service.interfaces.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    @Override
    public List<Group> getGroupsByUserId(String userId) {
        return groupRepository.getGroupsByUserUserId(userId);
    }

    @Override
    public void saveGroup(Group group) {
        groupRepository.save(group);
    }

    @Override
    public void deleteGroup(String groupId) {
        groupRepository.deleteById(groupId);
    }
}
