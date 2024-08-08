package ru.effective_mobile.tasks.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.effective_mobile.tasks.config.ExtendedModelMapper;
import ru.effective_mobile.tasks.dto.TaskDto;
import ru.effective_mobile.tasks.model.Task;
import ru.effective_mobile.tasks.repository.TaskRepository;
import ru.effective_mobile.tasks.service.TaskService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskServiceImpl implements TaskService {

    TaskRepository taskRepository;
    ModelMapper modelMapper;
    ExtendedModelMapper extendedModelMapper;

    @Override
    public Optional<TaskDto> getById(Long id) {
        return Optional.ofNullable(modelMapper.map(taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found")), TaskDto.class));
    }

    @Override
    public Optional<List<TaskDto>> getAllByAuthorId(Long id) {
        Optional<List<Task>> tasksFromRepo = Optional.ofNullable(taskRepository.findAllByAuthorId(id).orElseThrow(() -> new EntityNotFoundException("Tasks not found")));
        return Optional.ofNullable(extendedModelMapper.mapList(tasksFromRepo.get(), TaskDto.class));
    }

    @Override
    public Optional<TaskDto> create(TaskDto taskDto) {
        return Optional.ofNullable(modelMapper.map(taskRepository.save(modelMapper.map(taskDto, Task.class)),TaskDto.class));
    }

    @Override
    public Optional<TaskDto> edit(TaskDto taskDto) {
        Optional<TaskDto> taskFromRepo = getById(taskDto.getId());
        if (taskDto.getDescription() != null) {
            taskFromRepo.get().setDescription(taskDto.getDescription());
        }
        if (taskDto.getStatus() != null) {
            taskFromRepo.get().setStatus(taskDto.getStatus());
        }
        if (taskDto.getPriority() != null) {
            taskFromRepo.get().setPriority(taskDto.getPriority());
        }
        if (taskDto.getTitle() != null) {
            taskFromRepo.get().setTitle(taskDto.getTitle());
        }
        if (taskDto.getPerformerId() != null) {
            taskFromRepo.get().setPerformerId(taskDto.getPerformerId());
        }
        return Optional.ofNullable(modelMapper.map(taskRepository.save(modelMapper.map(taskFromRepo, Task.class)), TaskDto.class));
    }

    @Override
    public Optional<TaskDto> editStatus(TaskDto taskDto) {
        Optional<TaskDto> taskFromRepo = getById(taskDto.getId());
        taskFromRepo.get().setStatus(taskDto.getStatus());
        return Optional.ofNullable(modelMapper.map(taskRepository.save(modelMapper.map(taskFromRepo, Task.class)), TaskDto.class));
    }

    @Override
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }
}
