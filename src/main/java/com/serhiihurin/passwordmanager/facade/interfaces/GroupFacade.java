package com.serhiihurin.passwordmanager.facade.interfaces;

import com.serhiihurin.passwordmanager.entity.Group;
import com.serhiihurin.passwordmanager.entity.User;

import java.util.List;

public interface GroupFacade {
    List<Group> getGroupsByUserId(User currentAuthenticatedUser);
    void createGroup(Group group);
    void deleteGroup(String groupId);
}
