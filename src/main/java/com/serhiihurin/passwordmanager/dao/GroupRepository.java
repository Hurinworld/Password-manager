package com.serhiihurin.passwordmanager.dao;

import com.serhiihurin.passwordmanager.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupRepository extends JpaRepository<Group, String> {
    List<Group> getGroupsByUserUserId(String userId);

    void deleteGroupByTitle(String title);
}
