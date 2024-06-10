package com.serhiihurin.passwordmanager.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "records")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Record {
    @Id
    private String recordId;
    private String title;
    private String description;
//    private String group;
    private String username;
    private String password;
    private String url;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    public String getGroupName() {
        if (group == null) {
            return  null;
        } else {
            return group.getTitle();
        }
    }
}
