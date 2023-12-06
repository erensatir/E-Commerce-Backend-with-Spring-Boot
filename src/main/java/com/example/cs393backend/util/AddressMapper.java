package com.example.cs393backend.util;

import com.example.cs393backend.dto.AddressDto;
import com.example.cs393backend.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressDto toDto(AddressEntity addressEntity);
    AddressEntity toEntity(AddressDto addressDto);
    void updateAddressFromDto(AddressDto dto, @MappingTarget AddressEntity entity);
}
