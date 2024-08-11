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
import ru.effective_mobile.tasks.service.UserAuthenticationService;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TaskServiceImpl implements TaskService {

    TaskRepository taskRepository;
    ModelMapper modelMapper;
    ExtendedModelMapper extendedModelMapper;
    UserAuthenticationService userAuthenticationService;

    @Override
    public TaskDto getById(Long id) {
        return modelMapper.map(taskRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Task not found")), TaskDto.class);
    }

    @Override
    public List<TaskDto> getAllByAuthorId(Long id) {
        List<Task> tasksFromRepo = taskRepository.findAllByAuthorId(id).orElseThrow(() -> new EntityNotFoundException("Tasks not found"));
        return extendedModelMapper.mapList(tasksFromRepo, TaskDto.class);
    }

    @Override
    public TaskDto create(TaskDto taskDto) {
        taskDto.setId(null);
        taskDto.setAuthor(userAuthenticationService.getCurrent());
        return modelMapper.map(taskRepository.save(modelMapper.map(taskDto, Task.class)), TaskDto.class);
    }

    @Override
    public TaskDto edit(TaskDto taskDto) {
        TaskDto taskFromRepo = getById(taskDto.getId());
        if (!taskFromRepo.getAuthor().getId().equals(userAuthenticationService.getCurrent().getId())) {
            throw new SecurityException("You have no access to task with " + taskDto.getId() + " id");
        } else {
            if (taskDto.getDescription() != null) {
                taskFromRepo.setDescription(taskDto.getDescription());
            }
            if (taskDto.getStatus() != null) {
                taskFromRepo.setStatus(taskDto.getStatus());
            }
            if (taskDto.getPriority() != null) {
                taskFromRepo.setPriority(taskDto.getPriority());
            }
            if (taskDto.getTitle() != null) {
                taskFromRepo.setTitle(taskDto.getTitle());
            }
            if (taskDto.getPerformer() != null) {
                taskFromRepo.setPerformer(taskDto.getPerformer());
            }
            return modelMapper.map(taskRepository.save(modelMapper.map(taskFromRepo, Task.class)), TaskDto.class);
        }
    }

    @Override
    public TaskDto editStatus(TaskDto taskDto) {
        TaskDto taskFromRepo = getById(taskDto.getId());
        if (!taskFromRepo.getPerformer().getId().equals(userAuthenticationService.getCurrent().getId()) || !taskFromRepo.getAuthor().getId().equals(userAuthenticationService.getCurrent().getId())) {
            throw new SecurityException("You have no access to task with " + taskDto.getId() + " id");
        } else {
            taskFromRepo.setStatus(taskDto.getStatus());
            return modelMapper.map(taskRepository.save(modelMapper.map(taskFromRepo, Task.class)), TaskDto.class);
        }
    }

    @Override
    public void delete(Long id) {
        TaskDto taskFromRepo = getById(id);
        if (!taskFromRepo.getAuthor().getId().equals(userAuthenticationService.getCurrent().getId())) {
            throw new SecurityException("You have no access to task with " + id + " id");
        } else {
            taskRepository.deleteById(id);
        }
    }
}
