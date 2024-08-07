package ru.effective_mobile.tasks.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommentDto {
    Long id;
    String text;
    TaskDto task;
    UserDto user;
}
