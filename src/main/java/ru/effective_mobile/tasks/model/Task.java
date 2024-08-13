package ru.effective_mobile.tasks.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task extends AbstractModel {
    @Column(name = "id", nullable = false)
    Long id;
    @Column(name = "title", nullable = false)
    String title;
    @Column(name = "description", nullable = false)
    String description;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    TaskStatus status;
    @Column(name = "priority", nullable = false)
    @Enumerated(EnumType.STRING)
    TaskPriority priority;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id", nullable = false)
    User author;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "performer_id", nullable = false)
    User performer;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    List<Comment> comments;
}
