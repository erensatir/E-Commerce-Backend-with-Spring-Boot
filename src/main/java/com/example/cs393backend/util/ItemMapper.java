package com.example.cs393backend.util;

import com.example.cs393backend.dto.ItemDto;
import com.example.cs393backend.entity.ItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemDto toDto(ItemEntity itemEntity);
    ItemEntity toEntity(ItemDto itemDto);
    void updateItemFromDto(ItemDto dto, @MappingTarget ItemEntity entity);
}
