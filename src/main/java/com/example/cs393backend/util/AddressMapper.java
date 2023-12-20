package com.example.cs393backend.util;

import com.example.cs393backend.dto.AddressDto;
import com.example.cs393backend.entity.AddressEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto addressEntityToDto(AddressEntity addressEntity);
    AddressEntity addressDtoToEntity(AddressDto addressDto);
}
