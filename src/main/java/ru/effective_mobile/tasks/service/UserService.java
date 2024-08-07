package ru.effective_mobile.tasks.service;

import org.apache.catalina.User;
import ru.effective_mobile.tasks.dto.UserDto;

import java.util.Optional;

public interface UserService {

    Optional<UserDto> getById(Long id);

    Optional<UserDto> create(UserDto userDto);

    Optional<UserDto> edit(UserDto userDto);

    void delete(Long id);

}
