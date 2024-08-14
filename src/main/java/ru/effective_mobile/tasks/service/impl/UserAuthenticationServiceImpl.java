package ru.effective_mobile.tasks.service.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import ru.effective_mobile.tasks.dto.UserDto;
import ru.effective_mobile.tasks.model.User;
import ru.effective_mobile.tasks.repository.UserRepository;
import ru.effective_mobile.tasks.service.UserAuthenticationService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserAuthenticationServiceImpl implements UserAuthenticationService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    ModelMapper modelMapper;

    @Override
    public UserDto getCurrent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return modelMapper.map(getByEmail(currentPrincipalName), UserDto.class);
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found"));
    }

    @Override
    public User getEncryptedUserCredentials(UserDto userDto) {
        User userFromRepoByEmail = getByEmail(userDto.getEmail());
        if (!ObjectUtils.isEmpty(userFromRepoByEmail)) {
            if (passwordEncoder.matches(userDto.getPassword(), userFromRepoByEmail.getPassword())) {
                return userFromRepoByEmail;
            }
        }
        throw new SecurityException("Incorrect email or password");
    }
}
