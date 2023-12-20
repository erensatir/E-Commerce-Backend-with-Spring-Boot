package com.example.cs393backend.util;

import com.example.cs393backend.dto.UserDto;
import com.example.cs393backend.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userEntityToDto(UserEntity userEntity);
    UserEntity userDtoToEntity(UserDto userDto);

    void updateUserFromDto(UserDto userDto, @MappingTarget UserEntity userEntity);
}
