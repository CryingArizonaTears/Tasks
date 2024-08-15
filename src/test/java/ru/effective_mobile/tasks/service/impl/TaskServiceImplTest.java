package ru.effective_mobile.tasks.service.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import ru.effective_mobile.tasks.config.ExtendedModelMapper;
import ru.effective_mobile.tasks.dto.TaskDto;
import ru.effective_mobile.tasks.dto.UserDto;
import ru.effective_mobile.tasks.model.Task;
import ru.effective_mobile.tasks.model.TaskPriority;
import ru.effective_mobile.tasks.model.TaskStatus;
import ru.effective_mobile.tasks.model.User;
import ru.effective_mobile.tasks.repository.TaskRepository;
import ru.effective_mobile.tasks.service.UserAuthenticationService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class TaskServiceImplTest {

    @InjectMocks
    TaskServiceImpl taskService;

    @Mock
    TaskRepository taskRepository;

    @Mock
    ModelMapper modelMapper;

    @Mock
    ExtendedModelMapper extendedModelMapper;

    @Mock
    UserAuthenticationService userAuthenticationService;

    Task task;
    TaskDto taskDto;
    User author;
    User performer;
    UserDto authorDto;
    UserDto performerDto;

    @BeforeEach
    void setUp() {
        author = new User();
        author.setId(1L);

        performer = new User();
        performer.setId(2L);

        authorDto = new UserDto();
        authorDto.setId(1L);

        performerDto = new UserDto();
        performerDto.setId(2L);

        task = new Task();
        task.setId(1L);
        task.setTitle("testTask");
        task.setDescription("testDescription");
        task.setStatus(TaskStatus.OPENED);
        task.setPriority(TaskPriority.HIGH);
        task.setAuthor(author);
        task.setPerformer(performer);

        taskDto = new TaskDto();
        taskDto.setId(1L);
        taskDto.setTitle("testTask");
        taskDto.setDescription("testDescription");
        taskDto.setStatus(TaskStatus.OPENED);
        taskDto.setPriority(TaskPriority.HIGH);
        taskDto.setAuthor(authorDto);
        taskDto.setPerformer(performerDto);
    }

    @Test
    void testGetById_Successful() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(modelMapper.map(task, TaskDto.class)).thenReturn(taskDto);

        TaskDto foundTask = taskService.getById(1L);

        assertEquals(1L, foundTask.getId());
        assertEquals("testTask", foundTask.getTitle());
        assertEquals("testDescription", foundTask.getDescription());
        assertEquals(TaskStatus.OPENED, foundTask.getStatus());
        assertEquals(TaskPriority.HIGH, foundTask.getPriority());
        assertEquals(1L, foundTask.getAuthor().getId());
        assertEquals(2L, foundTask.getPerformer().getId());
    }

    @Test
    void testGetAllByAuthorId_Successful() {
        List<Task> tasks = List.of(task);
        List<TaskDto> taskDtos = List.of(taskDto);

        when(taskRepository.findAllByAuthorId(1L)).thenReturn(Optional.of(tasks));
        when(extendedModelMapper.mapList(tasks, TaskDto.class)).thenReturn(taskDtos);

        List<TaskDto> foundTasks = taskService.getAllByAuthorId(1L);

        assertEquals(1, foundTasks.size());
        assertEquals(1L, foundTasks.get(0).getId());
        assertEquals("testTask", foundTasks.get(0).getTitle());
        assertEquals("testDescription", foundTasks.get(0).getDescription());
        assertEquals(TaskStatus.OPENED, foundTasks.get(0).getStatus());
        assertEquals(TaskPriority.HIGH, foundTasks.get(0).getPriority());
        assertEquals(1L, foundTasks.get(0).getAuthor().getId());
        assertEquals(2L, foundTasks.get(0).getPerformer().getId());
    }

    @Test
    void testCreateTask_Successful() {
        when(userAuthenticationService.getCurrent()).thenReturn(authorDto);
        when(modelMapper.map(taskDto, Task.class)).thenReturn(task);
        when(taskRepository.save(task)).thenReturn(task);
        when(modelMapper.map(task, TaskDto.class)).thenReturn(taskDto);

        TaskDto createdTask = taskService.create(taskDto);

        assertNull(createdTask.getId());
        assertEquals("testTask", createdTask.getTitle());
        assertEquals("testDescription", createdTask.getDescription());
        assertEquals(TaskStatus.OPENED, createdTask.getStatus());
        assertEquals(TaskPriority.HIGH, createdTask.getPriority());
        assertEquals(1L, createdTask.getAuthor().getId());
        assertEquals(2L, createdTask.getPerformer().getId());
    }

    @Test
    void testEditTask_Successful() {
        taskDto.setTitle("editedTitle");
        taskDto.setDescription("editedDescription");
        taskDto.setStatus(TaskStatus.PAUSED);
        taskDto.setPriority(TaskPriority.LOW);

        when(userAuthenticationService.getCurrent()).thenReturn(authorDto);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(modelMapper.map(any(Task.class), eq(TaskDto.class))).thenReturn(taskDto);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDto updatedTask = taskService.edit(taskDto);

        assertEquals("editedTitle", updatedTask.getTitle());
        assertEquals("editedDescription", updatedTask.getDescription());
        assertEquals(TaskStatus.PAUSED, updatedTask.getStatus());
        assertEquals(TaskPriority.LOW, updatedTask.getPriority());
        assertEquals(1L, updatedTask.getAuthor().getId());
        assertEquals(2L, updatedTask.getPerformer().getId());
    }

    @Test
    void testEditStatus_Successful() {
        taskDto.setStatus(TaskStatus.CLOSED);

        when(userAuthenticationService.getCurrent()).thenReturn(performerDto);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(modelMapper.map(any(Task.class), eq(TaskDto.class))).thenReturn(taskDto);
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskDto updatedTask = taskService.editStatus(taskDto);

        assertEquals(TaskStatus.CLOSED, updatedTask.getStatus());
    }

    @Test
    void testDeleteTask_Successful() {
        when(userAuthenticationService.getCurrent()).thenReturn(authorDto);
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.delete(1L);

        verify(taskRepository, times(1)).deleteById(1L);
    }
}