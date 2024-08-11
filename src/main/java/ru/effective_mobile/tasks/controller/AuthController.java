package ru.effective_mobile.tasks.controller;

import jakarta.annotation.security.PermitAll;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    public ResponseEntity<UserDto> create(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.create(userDto));
    }

    @PostMapping("/auth")
    public ResponseEntity<String> logIn(@RequestBody UserDto UserDto) {
        User userLogin = userAuthenticationService.getEncryptedUserCredentials(UserDto);
        String token = tokenProvider.createToken(userLogin.getEmail());
        return ResponseEntity.ok(token);
    }
}
