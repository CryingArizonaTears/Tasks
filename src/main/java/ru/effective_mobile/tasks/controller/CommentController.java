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
import ru.effective_mobile.tasks.dto.CommentDto;
import ru.effective_mobile.tasks.service.CommentService;

import java.util.List;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "/comment")
public class CommentController {

    CommentService commentService;

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @PostMapping()
    @Operation(
            summary = "Create new comment for a task",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comment is created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))}),
                    @ApiResponse(responseCode = "400", description = "Request body has invalid fields"),
                    @ApiResponse(responseCode = "403", description = "User is anonymous"),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(mediaType = "application/json", examples = @ExampleObject(value = "{\n\"text\": \"testComment1\",\n\"task\": {\n\"id\": 1\n}\n}")))
    )
    public ResponseEntity<CommentDto> create(@Validated(CommentDto.CreateValidationGroup.class) @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.addToTask(commentDto), HttpStatus.CREATED);
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    @Operation(
            summary = "Get comment by id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comment is found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))}),
                    @ApiResponse(responseCode = "400", description = "Comment not found"),
                    @ApiResponse(responseCode = "403", description = "User is anonymous"),
            })
    public ResponseEntity<CommentDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getById(id));
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @GetMapping("/task/{id}")
    @Operation(
            summary = "Get comments by task id",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comments are found", content = {@Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CommentDto.class)))}),
                    @ApiResponse(responseCode = "400", description = "Comments not found"),
                    @ApiResponse(responseCode = "403", description = "User is anonymous"),
            })
    public ResponseEntity<List<CommentDto>> getByTaskId(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getAllByTaskId(id));
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @PutMapping()
    @Operation(
            summary = "Edit comment text",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comment has been edited", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CommentDto.class))}),
                    @ApiResponse(responseCode = "400", description = "Request body has invalid fields"),
                    @ApiResponse(responseCode = "403", description = "User is anonymous")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject("{\n\"email\": \"testEmail1Edit@mail.com\",\n\"password\": \"testPassword1Edit\"\n, \n\"name\": \"testName1Edit\"\n}")))
    )
    public ResponseEntity<CommentDto> edit(@Validated(CommentDto.EditValidationGroup.class) @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.edit(commentDto));
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete comment",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Comment has been deleted"),
                    @ApiResponse(responseCode = "403", description = "User is anonymous")
            })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
