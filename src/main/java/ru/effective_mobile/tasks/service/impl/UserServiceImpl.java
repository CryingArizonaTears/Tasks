package ru.effective_mobile.tasks.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.effective_mobile.tasks.dto.UserDto;
import ru.effective_mobile.tasks.model.User;
import ru.effective_mobile.tasks.repository.UserRepository;
import ru.effective_mobile.tasks.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    ModelMapper modelMapper;
    PasswordEncoder passwordEncoder;

    @SneakyThrows
    @Override
    public Optional<UserDto> getById(Long id) {
        return Optional.ofNullable(modelMapper.map(userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found")), UserDto.class));
    }

    @Override
    public Optional<UserDto> create(UserDto userDto) {
        userDto.setId(null);
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        return Optional.ofNullable(modelMapper.map(userRepository.save(modelMapper.map(userDto, User.class)), UserDto.class));
    }

    @Override
    public Optional<UserDto> edit(UserDto userDto) {
        Optional<UserDto> userForEdit = getById(userDto.getId());
        if (userDto.getEmail() != null) {
            userForEdit.get().setEmail(userDto.getEmail());
        }
        if (userDto.getPassword() != null) {
            userForEdit.get().setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        if (userDto.getName() != null) {
            userForEdit.get().setName(userDto.getName());
        }
        return Optional.ofNullable(modelMapper.map(userRepository.save(modelMapper.map(userDto, User.class)), UserDto.class));
    }

    @Override
    public void delete(Long id) {
        Optional<UserDto> userDto = getById(id);
        userRepository.delete(modelMapper.map(userDto, User.class));
    }
}
