package ru.effective_mobile.tasks.service;

import ru.effective_mobile.tasks.dto.UserDto;
import ru.effective_mobile.tasks.model.User;

import java.util.Optional;

public interface UserAuthenticationService {

    User getCurrent();

    Optional<User> getByEmail(String email);

    User getEncryptedUserCredentials(UserDto userDto);
}
