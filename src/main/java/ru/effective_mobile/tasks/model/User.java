package ru.effective_mobile.tasks.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class User extends AbstractModel {
    @Column(name = "id", nullable = false)
    Long id;
    @Column(name = "email", nullable = false)
    String email;
    @Column(name = "password", nullable = false)
    String password;
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    Role role;
    @Column(name = "name", nullable = false)
    String name;
}
