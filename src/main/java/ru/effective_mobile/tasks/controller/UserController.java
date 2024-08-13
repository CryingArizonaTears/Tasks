package ru.effective_mobile.tasks.controller;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.effective_mobile.tasks.dto.UserDto;
import ru.effective_mobile.tasks.service.UserService;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping(value = "/user")
public class UserController {

    UserService userService;

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @PutMapping()
    public ResponseEntity<UserDto> edit(@Validated(UserDto.EditValidationGroup.class) @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.edit(userDto));
    }

    @PreAuthorize(value = "hasRole('ROLE_USER')")
    @DeleteMapping()
    public ResponseEntity<Void> delete() {
        userService.delete();
        return ResponseEntity.noContent().build();
    }
}
