package ru.effective_mobile.tasks.service;

import ru.effective_mobile.tasks.dto.UserDto;

public interface UserService {

    UserDto getById(Long id);

    UserDto create(UserDto userDto);

    UserDto edit(UserDto userDto);

    void delete(Long id);

}
