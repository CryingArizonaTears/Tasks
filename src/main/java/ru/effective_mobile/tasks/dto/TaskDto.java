package ru.effective_mobile.tasks.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import ru.effective_mobile.tasks.model.TaskPriority;
import ru.effective_mobile.tasks.model.TaskStatus;

import java.util.List;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TaskDto {
    @NotNull(message = "Title cannot be null", groups = {EditValidationGroup.class, EditStatusValidationGroup.class})
    Long id;
    @NotEmpty(message = "Title cannot be null", groups = CreateValidationGroup.class)
    String title;
    @NotEmpty(message = "Description cannot be null", groups = CreateValidationGroup.class)
    String description;
    @NotNull(message = "Status cannot be null", groups = {CreateValidationGroup.class, EditStatusValidationGroup.class})
    TaskStatus status;
    @NotNull(message = "Priority cannot be null", groups = CreateValidationGroup.class)
    TaskPriority priority;
    UserDto author;
    @NotNull(message = "Performer cannot be null", groups = CreateValidationGroup.class)
    UserDto performer;
    List<CommentDto> comments;

    public interface CreateValidationGroup {
    }

    public interface EditValidationGroup {
    }

    public interface EditStatusValidationGroup {
    }
}
