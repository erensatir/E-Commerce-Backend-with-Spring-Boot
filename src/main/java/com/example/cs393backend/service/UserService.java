package com.example.cs393backend.service;

import com.example.cs393backend.dto.UserDto;
import com.example.cs393backend.entity.UserEntity;
import com.example.cs393backend.util.UserMapper;
import com.example.cs393backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(userMapper::userEntityToDto)
                .collect(Collectors.toList());
    }

    public UserDto findById(Long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.userEntityToDto(user);
    }

    public UserDto createUser(UserDto userDto) {
        UserEntity userEntity = userMapper.userDtoToEntity(userDto);
        UserEntity savedUser = userRepository.save(userEntity);
        return userMapper.userEntityToDto(savedUser);
    }


    public UserDto updateUser(Long id, UserDto userDto) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUserFromDto(userDto, existingUser);
        userRepository.save(existingUser);
        return userMapper.userEntityToDto(existingUser);
    }


    public void deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new RuntimeException("User not found");
        }
    }

}
