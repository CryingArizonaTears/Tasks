package ru.effective_mobile.tasks.service;

import ru.effective_mobile.tasks.dto.UserDto;
import ru.effective_mobile.tasks.model.User;

public interface UserAuthenticationService {

    UserDto getCurrent();

    User getByEmail(String email);

    User getEncryptedUserCredentials(UserDto userDto);
}
