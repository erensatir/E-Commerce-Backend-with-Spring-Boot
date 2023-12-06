package com.example.cs393backend.util;

import com.example.AmazonBackendClone.dto.UserDto;
import com.example.cs393backend.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

// UserMapper.java
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(UserEntity userEntity);
    UserEntity toEntity(UserDto userDto);
    void updateUserFromDto(UserDto dto, @MappingTarget UserEntity entity);
}
