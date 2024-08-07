package ru.effective_mobile.tasks.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import ru.effective_mobile.tasks.dto.TaskDto;
import ru.effective_mobile.tasks.service.TaskService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskServiceImpl implements TaskService {



    @Override
    public Optional<TaskDto> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<List<TaskDto>> getAllByUserId(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<TaskDto> create(TaskDto taskDto) {
        return Optional.empty();
    }

    @Override
    public Optional<TaskDto> edit(TaskDto taskDto) {
        return Optional.empty();
    }

    @Override
    public Optional<TaskDto> editStatus(TaskDto taskDto) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {

    }
}
