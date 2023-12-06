package com.example.cs393backend.service;

import com.example.cs393backend.dto.UserDto;
import com.example.cs393backend.entity.UserEntity;
import com.example.cs393backend.repository.UserRepository;
import com.example.cs393backend.util.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

// UserService.java
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = userMapper.toEntity(userDto);
        userRepository.save(userEntity);
        return userMapper.toDto(userEntity);
    }

    public UserDto getUser(Long id) {
        UserEntity userEntity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(userEntity);
    }

    public List<UserDto> getAllUsers() {
        List<UserEntity> userEntities = userRepository.findAll();
        return userEntities.stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList());
    }

    public UserDto updateUser(UserDto userDto) {
        UserEntity userEntity = userRepository.findById(userDto.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUserFromDto(userDto, userEntity);
        userRepository.save(userEntity);
        return userMapper.toDto(userEntity);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
