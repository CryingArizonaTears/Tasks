package ru.effective_mobile.tasks.service;

import ru.effective_mobile.tasks.dto.TaskDto;

import java.util.List;

public interface TaskService {

    TaskDto getById(Long id);

    List<TaskDto> getAllByAuthorId(Long id);

    TaskDto create(TaskDto taskDto);

    TaskDto edit(TaskDto taskDto);

    TaskDto editStatus(TaskDto taskDto);

    void delete(Long id);

}
