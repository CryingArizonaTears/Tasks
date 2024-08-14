package ru.effective_mobile.tasks.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary = "Create new task",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task is created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TaskDto.class))}),
                    @ApiResponse(responseCode = "400", description = "Request body has invalid fields"),
                    @ApiResponse(responseCode = "403", description = "User is anonymous"),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\n\"title\": \"testTask1\",\n\"description\": \"testDescription1\",\n\"status\": \"OPENED\",\n\"priority\": \"HIGH\",\n\"performer\": {\n\"id\": 1\n}\n}")))

    )
    public ResponseEntity<TaskDto> create(@Validated(TaskDto.CreateValidationGroup.class) @RequestBody TaskDto taskDto) {
        return new ResponseEntity<>(taskService.create(taskDto), HttpStatus.CREATED);
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    @Operation(
            summary = "Get task by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task is found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TaskDto.class))}),
                    @ApiResponse(responseCode = "400", description = "Task not found"),
                    @ApiResponse(responseCode = "403", description = "User is anonymous"),
            })
    public ResponseEntity<TaskDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getById(id));
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @GetMapping("/author/{id}")
    @Operation(
            summary = "Get tasks by author id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tasks are found", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TaskDto.class)))}),
                    @ApiResponse(responseCode = "400", description = "Tasks not found"),
                    @ApiResponse(responseCode = "403", description = "User is anonymous"),
            })
    public ResponseEntity<List<TaskDto>> getByAuthorId(@PathVariable Long id) {
        return ResponseEntity.ok(taskService.getAllByAuthorId(id));
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @PutMapping()
    @Operation(
            summary = "Edit task title, description, status, priority or performer",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task has been edited", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TaskDto.class))}),
                    @ApiResponse(responseCode = "400", description = "Request body has invalid fields"),
                    @ApiResponse(responseCode = "403", description = "User is anonymous")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject(value = "{\n\"id\": 1,\n\"title\": \"testTaskEdit1\",\n\"description\": \"testDescriptionEdit1\",\n\"status\": \"PAUSED\",\n\"priority\": \"LOW\"\n}")))
    )
    public ResponseEntity<TaskDto> edit(@Validated({TaskDto.EditValidationGroup.class}) @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.edit(taskDto));
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @PutMapping("/status")
    @Operation(
            summary = "Edit task status",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task has been edited", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = TaskDto.class))}),
                    @ApiResponse(responseCode = "400", description = "Request body has invalid fields"),
                    @ApiResponse(responseCode = "403", description = "User is anonymous")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject(value = "{\n\"id\": 1,\n\"status\": \"CLOSED\"\n}")))
    )
    public ResponseEntity<TaskDto> editStatus(@Validated(TaskDto.EditStatusValidationGroup.class) @RequestBody TaskDto taskDto) {
        return ResponseEntity.ok(taskService.editStatus(taskDto));
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete task",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task has been deleted"),
                    @ApiResponse(responseCode = "403", description = "User is anonymous")
            })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
