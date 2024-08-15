package ru.effective_mobile.tasks.service.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.effective_mobile.tasks.dto.UserDto;
import ru.effective_mobile.tasks.model.Role;
import ru.effective_mobile.tasks.model.User;
import ru.effective_mobile.tasks.repository.UserRepository;
import ru.effective_mobile.tasks.service.UserAuthenticationService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    ModelMapper modelMapper;

    @Mock
    UserAuthenticationService userAuthenticationService;

    @InjectMocks
    UserServiceImpl userServiceImpl;

    UserDto userDto;
    User user;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setEmail("test@example.com");
        userDto.setPassword("password");
        userDto.setName("testUser");

        user = new User();
        user.setId(1L);
        user.setEmail("test@example.com");
        user.setPassword("encodedPassword");
        user.setRole(Role.ROLE_USER);
        user.setName("testUser");
    }

    @Test
    void testGetById_Successful() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(modelMapper.map(user, UserDto.class)).thenReturn(userDto);

        UserDto foundUser = userServiceImpl.getById(1L);

        assertEquals(1L, foundUser.getId());
        assertEquals("test@example.com", foundUser.getEmail());
    }

    @Test
    void testCreateUser_Successful() {
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(modelMapper.map(any(UserDto.class), any())).thenReturn(user);
        when(modelMapper.map(any(User.class), any())).thenReturn(userDto);
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserDto createdUser = userServiceImpl.create(userDto);

        assertNull(createdUser.getId());
        assertEquals(Role.ROLE_USER, createdUser.getRole());
        assertEquals("encodedPassword", createdUser.getPassword());
    }

    @Test
    void testEditUser_Successful() {
        when(userAuthenticationService.getCurrent()).thenReturn(userDto);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode("editedPassword")).thenReturn("encodedEditedPassword");
        when(modelMapper.map(any(UserDto.class), any())).thenReturn(user);
        when(modelMapper.map(any(User.class), any())).thenReturn(userDto);
        when(userRepository.save(any(User.class))).thenReturn(user);

        userDto.setEmail("editedEmail@example.com");
        userDto.setPassword("editedPassword");
        userDto.setName("editedName");

        UserDto updatedUser = userServiceImpl.edit(userDto);

        assertEquals("editedEmail@example.com", updatedUser.getEmail());
        assertEquals("encodedEditedPassword", updatedUser.getPassword());
        assertEquals("editedName", updatedUser.getName());
    }

    @Test
    void testDeleteUser_Successful() {
        when(userAuthenticationService.getCurrent()).thenReturn(userDto);

        userServiceImpl.delete();

        verify(userRepository, times(1)).deleteById(1L);
    }
}