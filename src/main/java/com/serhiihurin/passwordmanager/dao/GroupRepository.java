package com.serhiihurin.passwordmanager.dao;

import com.serhiihurin.passwordmanager.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupRepository extends JpaRepository<Group, Long> {
}
