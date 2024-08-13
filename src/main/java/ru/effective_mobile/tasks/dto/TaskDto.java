package ru.effective_mobile.tasks.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.effective_mobile.tasks.model.TaskPriority;
import ru.effective_mobile.tasks.model.TaskStatus;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDto {
    @NotEmpty(message = "Title cannot be null", groups = {EditValidationGroup.class, EditStatusValidationGroup.class})
    Long id;
    @NotEmpty(message = "Title cannot be null", groups = CreateValidationGroup.class)
    String title;
    @NotEmpty(message = "Description cannot be null", groups = CreateValidationGroup.class)
    String description;
    @NotEmpty(message = "Status cannot be null", groups = {CreateValidationGroup.class, EditStatusValidationGroup.class})
    TaskStatus status;
    @NotEmpty(message = "Priority cannot be null", groups = CreateValidationGroup.class)
    TaskPriority priority;
    UserDto author;
    @NotEmpty(message = "Performer cannot be null", groups = CreateValidationGroup.class)
    UserDto performer;
    List<CommentDto> comments;

    public interface CreateValidationGroup {}
    public interface EditValidationGroup {}
    public interface EditStatusValidationGroup {}
}
