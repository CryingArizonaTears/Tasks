package ru.effective_mobile.tasks.controller;

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
    public ResponseEntity<CommentDto> create(@Validated(CommentDto.CreateValidationGroup.class) @RequestBody CommentDto commentDto) {
        return new ResponseEntity<>(commentService.addToTask(commentDto), HttpStatus.CREATED);
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getById(id));
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @GetMapping("/task/{id}")
    public ResponseEntity<List<CommentDto>> getByTaskId(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getAllByTaskId(id));
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @PutMapping()
    public ResponseEntity<CommentDto> edit(@Validated(CommentDto.EditValidationGroup.class) @RequestBody CommentDto commentDto) {
        return ResponseEntity.ok(commentService.edit(commentDto));
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        commentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
