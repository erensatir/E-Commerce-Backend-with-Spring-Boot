package com.example.cs393backend.ServiceTests;

import com.example.cs393backend.dto.UserDto;
import com.example.cs393backend.entity.UserEntity;
import com.example.cs393backend.repository.UserRepository;
import com.example.cs393backend.service.UserService;
import com.example.cs393backend.util.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private UserDto userDto;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userDto = new UserDto();
        userDto.setId(1L);
        userDto.setUsername("testUser");
        userDto.setEmail("test@example.com");

        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("testUser");
        userEntity.setEmail("test@example.com");
    }

    @Test
    void findAllUsers() {
        when(userRepository.findAll()).thenReturn(Collections.singletonList(userEntity));
        when(userMapper.userEntityToDto(any(UserEntity.class))).thenReturn(userDto);

        List<UserDto> result = userService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(userDto, result.get(0));

        verify(userRepository).findAll();
        verify(userMapper).userEntityToDto(any(UserEntity.class));
    }

    @Test
    void findUserById() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(userEntity));
        when(userMapper.userEntityToDto(any(UserEntity.class))).thenReturn(userDto);

        UserDto result = userService.findById(1L);

        assertNotNull(result);
        assertEquals(userDto, result);

        verify(userRepository).findById(anyLong());
        verify(userMapper).userEntityToDto(any(UserEntity.class));
    }

    @Test
    void createUser() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(userMapper.userDtoToEntity(any(UserDto.class))).thenReturn(userEntity);
        when(userMapper.userEntityToDto(any(UserEntity.class))).thenReturn(userDto);

        UserDto result = userService.createUser(userDto);

        assertNotNull(result);
        assertEquals(userDto, result);

        verify(userRepository).save(any(UserEntity.class));
        verify(userMapper).userDtoToEntity(any(UserDto.class));
        verify(userMapper).userEntityToDto(any(UserEntity.class));
    }

    // Similar tests can be written for updateUser and deleteUser methods
}
