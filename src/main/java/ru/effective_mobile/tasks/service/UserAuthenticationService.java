package ru.effective_mobile.tasks.service;

import ru.effective_mobile.tasks.model.User;

public interface UserAuthenticationService {

    User getCurrent();

    User getByEmail(String email);

    User getEncryptedUserCredentials(User user);
}
