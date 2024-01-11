package com.example.cs393backend.service;

import com.example.cs393backend.dto.UserDto;
import com.example.cs393backend.entity.UserEntity;
import com.example.cs393backend.util.UserMapper;
import com.example.cs393backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
        if (userRepository.findByEmail(userDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already in use");
        }
        if (userRepository.findByPhoneNumber(userDto.getPhoneNumber()).isPresent()) {
            throw new RuntimeException("Phone number already in use");
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        UserEntity userEntity = userMapper.userDtoToEntity(userDto);
        UserEntity savedUser = userRepository.save(userEntity);
        return userMapper.userEntityToDto(savedUser);
    }

    public UserDto updateUser(Long id, UserDto userDto) {
        UserEntity existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userRepository.findByEmail(userDto.getEmail())
                .ifPresent(user -> {
                    if (!user.getId().equals(id)) {
                        throw new RuntimeException("Email already in use");
                    }
                });
        userRepository.findByPhoneNumber(userDto.getPhoneNumber())
                .ifPresent(user -> {
                    if (!user.getId().equals(id)) {
                        throw new RuntimeException("Phone number already in use");
                    }
                });

        userDto.setPassword(existingUser.getPassword());
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

    public UserDto resetPassword(Long id, String newPassword) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return userMapper.userEntityToDto(user);
    }


}
