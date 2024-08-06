package ru.effective_mobile.tasks.service;

import org.apache.catalina.User;

import java.util.Optional;

public interface UserService {

    Optional<User> create(User user);

    Optional<User> edit(User user);

    void delete(Long id);

}
