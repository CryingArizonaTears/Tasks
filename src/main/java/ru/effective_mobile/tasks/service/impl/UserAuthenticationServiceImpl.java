package ru.effective_mobile.tasks.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.effective_mobile.tasks.model.User;
import ru.effective_mobile.tasks.service.UserAuthenticationService;

public class UserAuthenticationServiceImpl implements UserAuthenticationService {
    @Override
    public User getCurrent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = authentication.get
        return null;
    }

    @Override
    public User getByEmail(String email) {
        return null;
    }

    @Override
    public User getEncryptedUserCredentials(User user) {
        return null;
    }
}
