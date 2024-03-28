package com.serhiihurin.passwordmanager.dao;

import com.serhiihurin.passwordmanager.entity.Record;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RecordRepository extends JpaRepository<Record, Long> {
    List<Record> getRecordsByUserUserId(Long userId);
//    @Query("SELECT r FROM Record r WHERE r.user.userId = :userId AND r.recordId = :recordId")
    Record getRecordByUserUserIdAndRecordId(Long userId, Long recordId);

    @Query("SELECT r FROM Record r " +
            "WHERE r.user.userId = :userId AND LOWER(r.title) LIKE LOWER(CONCAT('%', :filterText, '%'))")
    List<Record> search(@Param("userId") Long userId, @Param("filterText") String filterText);
}
