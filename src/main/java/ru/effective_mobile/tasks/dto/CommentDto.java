package ru.effective_mobile.tasks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {
    @NotEmpty(message = "Id cannot be null", groups = EditValidationGroup.class)
    Long id;
    @NotEmpty(message = "Text cannot be null", groups = {CreateValidationGroup.class, EditValidationGroup.class})
    String text;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotEmpty(message = "Text cannot be null", groups = CreateValidationGroup.class)
    TaskDto task;
    UserDto user;

    public interface CreateValidationGroup {}
    public interface EditValidationGroup {}
}
