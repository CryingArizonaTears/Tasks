package ru.effective_mobile.tasks.service;

import ru.effective_mobile.tasks.dto.TaskDto;

import java.util.List;
import java.util.Optional;

public interface TaskService {

    Optional<TaskDto> getById(Long id);

    Optional<List<TaskDto>> getAllByUserId(Long id);

    Optional<TaskDto> create(TaskDto taskDto);

    Optional<TaskDto> edit(TaskDto taskDto);

    Optional<TaskDto> editStatus(TaskDto taskDto);

    void delete(Long id);

}
