package com.serhiihurin.passwordmanager.dao;

import com.serhiihurin.passwordmanager.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, String> {
    List<Record> getRecordsByUserUserId(String userId);
//    @Query("SELECT r FROM Record r WHERE r.user.userId = :userId AND r.recordId = :recordId")
    Record getRecordByUserUserIdAndRecordId(String userId, String recordId);

    @Query("SELECT r FROM Record r " +
            "WHERE r.user.userId = :userId AND LOWER(r.title) LIKE LOWER(CONCAT('%', :filterText, '%'))")
    List<Record> search(@Param("userId") String userId, @Param("filterText") String filterText);
}
