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
            description = """
					Creating new user with email, password and name.
					""",
            responses = {
                    @ApiResponse(responseCode = "201", description = "User has been created and stays disable", content = @Content(mediaType = "application/json", schema = @Schema(implementation = JwtResponse.class))),
                    @ApiResponse(responseCode = "400", description = "User with provided email already exists or request body has invalid fields", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadValidationResponse.class))),
                    @ApiResponse(responseCode = "422", description = "Request body has invalid fields", content = @Content(mediaType = "application/json", schema = @Schema(implementation = BadValidationResponse.class))),
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject("{\n\"password\": \"helloworlD!\",\n\"matchingPassword\": \"helloworlD!\",\n\"email\": \"initial@gmail.com\",\n\"firstName\": \"John\",\n\"lastName\": \"Smith\"\n}")))
    )
    public ResponseEntity<UserDto> create(@Validated(UserDto.RegistrationValidationGroup.class) @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.create(userDto));
    }

    @Operation(
            summary = "Login via email and password", description = "Authorise user by email and password and get token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Token", content = {@Content(mediaType = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0NUBxd2UucXFxcSIsImV4cCI6MTcyMzY2OTIwMH0.xxk9mIUHzrNU6cCZsbOZraqPKL7MduOxeh2hsMJlORR8ZFvwFyGcHNlHgKIxSCXKtbv_izfNLqTh5xa5Gc9hZA")}),
                    @ApiResponse(responseCode = "400", description = "Full authentication is required to access this resource")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(examples = @ExampleObject("{\n\"email\": \"initial@gmail.com\",\n\"password\": \"helloworlD!\"\n}")))
    )
    @PostMapping("/auth")
    public ResponseEntity<String> logIn(@Validated @RequestBody UserDto UserDto) {
        User userLogin = userAuthenticationService.getEncryptedUserCredentials(UserDto);
        String token = tokenProvider.createToken(userLogin.getEmail());
        return ResponseEntity.ok(token);
    }
}
