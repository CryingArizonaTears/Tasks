package ru.effective_mobile.tasks.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.effective_mobile.tasks.dto.UserDto;
import ru.effective_mobile.tasks.model.User;
import ru.effective_mobile.tasks.security.filter.TokenProvider;
import ru.effective_mobile.tasks.service.UserAuthenticationService;
import ru.effective_mobile.tasks.service.UserService;

@RestController
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {

    UserService userService;
    UserAuthenticationService userAuthenticationService;
    TokenProvider tokenProvider;

    @PostMapping("/registration")
    @Operation(
            summary = "Create new user",
            description = "Creating new user with email, password and name",
            responses = {
                    @ApiResponse(responseCode = "200", description = "User has been created", content = @Content(mediaType = "application/json")),
                    @ApiResponse(responseCode = "400", description = "User with provided email already exists or request body has invalid fields", content = @Content(mediaType = "application/json")),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject("{\n\"email\": \"testemail1@mail.com\",\n\"password\": \"testpassword1\",\n\"name\": \"testname1\"}")))
    )
    public ResponseEntity<UserDto> create(@Validated(UserDto.RegistrationValidationGroup.class) @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.create(userDto));
    }


    @PostMapping("/auth")
    @Operation(
            summary = "Login via email and password", description = "Authorise user by email and password and get token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Token"),
                    @ApiResponse(responseCode = "400", description = "Request body has invalid fields")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject("{\n\"email\": \"testemail1@mail.com\",\n\"password\": \"testpassword1\"\n}")))
    )
    public ResponseEntity<String> logIn(@Validated @RequestBody UserDto UserDto) {
        User userLogin = userAuthenticationService.getEncryptedUserCredentials(UserDto);
        String token = tokenProvider.createToken(userLogin.getEmail());
        return ResponseEntity.ok(token);
    }
}
