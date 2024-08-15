package ru.effective_mobile.tasks.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.effective_mobile.tasks.dto.UserDto;
import ru.effective_mobile.tasks.model.Role;
import ru.effective_mobile.tasks.model.User;
import ru.effective_mobile.tasks.repository.UserRepository;
import ru.effective_mobile.tasks.service.UserService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private UserService userService;

    private UserDto userDto;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        userDto = new UserDto();
        userDto.setEmail("test@example.com");
        userDto.setPassword("password");
        userDto.setName("Test name");

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setRole(Role.ROLE_USER);
        user.setName("Test name");

        when(passwordEncoder.encode(userDto.getPassword())).thenReturn("encodedPassword");
        when(modelMapper.map(any(UserDto.class), any())).thenReturn(user);
        when(modelMapper.map(any(User.class), any())).thenReturn(userDto);
        when(userRepository.save(any(User.class))).thenReturn(user);
    }

    @Test
    void getById() {
    }

    @Test
    void create() {
        UserDto createdUser = userService.create(userDto);

        assertEquals("encodedPassword", createdUser.getPassword());
        assertEquals(Role.ROLE_USER, createdUser.getRole());
        assertEquals("test@example.com", createdUser.getEmail());
        assertEquals("Test name", createdUser.getName());
    }

    @Test
    void edit() {
    }

    @Test
    void delete() {
    }
}