package com.serhiihurin.passwordmanager.dao;

import com.serhiihurin.passwordmanager.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecordRepository extends JpaRepository<Record, Long> {
}
