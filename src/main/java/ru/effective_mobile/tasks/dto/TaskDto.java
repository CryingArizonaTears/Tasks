package ru.effective_mobile.tasks.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.effective_mobile.tasks.model.TaskPriority;
import ru.effective_mobile.tasks.model.TaskStatus;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDto {
    Long id;
    String title;
    String description;
    TaskStatus status;
    TaskPriority priority;
    UserDto author;
    UserDto performer;
}
