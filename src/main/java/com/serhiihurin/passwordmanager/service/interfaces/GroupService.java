package com.serhiihurin.passwordmanager.service.interfaces;

import com.serhiihurin.passwordmanager.entity.Group;

import java.util.List;

public interface GroupService {
    List<Group> getGroupsByUserId(String userId);

    void saveGroup(Group group);

    void deleteGroup(String groupId);
}
