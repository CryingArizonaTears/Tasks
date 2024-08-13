package ru.effective_mobile.tasks.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {
    Long id;
    String text;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    TaskDto task;
    UserDto user;
}
