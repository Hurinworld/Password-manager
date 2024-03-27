package com.serhiihurin.passwordmanager.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "record_groups")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Group {
    @Id
    private Long groupId;
    private String groupName;
    private Integer capacity;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "group")
    private List<Record> records;
}
