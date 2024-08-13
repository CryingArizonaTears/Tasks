package ru.effective_mobile.tasks.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.effective_mobile.tasks.dto.TaskDto;
import ru.effective_mobile.tasks.service.TaskService;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "/task")
public class TaskController {

    TaskService taskService;

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @PostMapping()
    public ResponseEntity<TaskDto> create(@Validated(TaskDto.CreateValidationGroup.class) @RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(taskService.create(taskDto), HttpStatus.CREATED);
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<TaskDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getById(id));
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @GetMapping("/author/{id}")
    public ResponseEntity<List<TaskDto>> getByAuthorId(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getAllByAuthorId(id));
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @PutMapping()
    public ResponseEntity<TaskDto> edit(@Validated({TaskDto.EditValidationGroup.class}) @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.edit(taskDto));
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @PutMapping("/status")
    public ResponseEntity<TaskDto> editStatus(@Validated(TaskDto.EditStatusValidationGroup.class) @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.editStatus(taskDto));
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
