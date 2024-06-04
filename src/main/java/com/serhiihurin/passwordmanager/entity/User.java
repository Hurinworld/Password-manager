package com.serhiihurin.passwordmanager.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class User implements UserDetails{
    @Id
    private String userId;
    @Email
    private String email;
    @NotEmpty
    private String masterPassword;
    @NotEmpty
    private String key;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;

    @ToString.Exclude
    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "user")
    private List<Record> records;

    @ToString.Exclude
    @OneToMany(cascade = {CascadeType.REMOVE}, mappedBy = "user")
    private List<Group> groups;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return masterPassword;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
