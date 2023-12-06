package com.example.cs393backend.util;

import com.example.cs393backend.dto.OrderDto;
import com.example.cs393backend.entity.OrderEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(OrderEntity orderEntity);
    OrderEntity toEntity(OrderDto orderDto);
    void updateOrderFromDto(OrderDto dto, @MappingTarget OrderEntity entity);
}
