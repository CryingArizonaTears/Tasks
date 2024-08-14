package ru.effective_mobile.tasks.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(
            summary = "Edit users email, password or name",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Token"),
                    @ApiResponse(responseCode = "400", description = "Request body has invalid fields")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject("{\n\"email\": \"testemail1@mail.com\",\n\"password\": \"testpassword1\"\n}")))
    )
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
