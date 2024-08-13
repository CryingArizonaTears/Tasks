package ru.effective_mobile.tasks.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.effective_mobile.tasks.dto.UserDto;
import ru.effective_mobile.tasks.model.Role;
import ru.effective_mobile.tasks.model.User;
import ru.effective_mobile.tasks.repository.UserRepository;
import ru.effective_mobile.tasks.service.UserAuthenticationService;
import ru.effective_mobile.tasks.service.UserService;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    ModelMapper modelMapper;
    PasswordEncoder passwordEncoder;
    UserAuthenticationService userAuthenticationService;

    @Override
    public UserDto getById(Long id) {
        return modelMapper.map(userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found")), UserDto.class);
    }

    @Override
    public UserDto create(UserDto userDto) {
        userDto.setId(null);
        userDto.setRole(Role.ROLE_USER);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return modelMapper.map(userRepository.save(modelMapper.map(userDto, User.class)), UserDto.class);
    }

    @Override
    public UserDto edit(UserDto userDto) {
        userDto.setId(userAuthenticationService.getCurrent().getId());
        UserDto userForEdit = getById(userDto.getId());
        if (userDto.getEmail() != null) {
            userForEdit.setEmail(userDto.getEmail());
        }
        if (userDto.getPassword() != null) {
            userForEdit.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        if (userDto.getName() != null) {
            userForEdit.setName(userDto.getName());
        }
        return modelMapper.map(userRepository.save(modelMapper.map(userDto, User.class)), UserDto.class);
    }

    @Override
    public void delete() {
        userRepository.deleteById(userAuthenticationService.getCurrent().getId());
    }
}
