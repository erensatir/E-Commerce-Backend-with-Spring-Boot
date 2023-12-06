package com.example.cs393backend.util;

import com.example.cs393backend.dto.ShoppingCartDto;
import com.example.cs393backend.entity.ShoppingCartEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ShoppingCartMapper {
    ShoppingCartDto toDto(ShoppingCartEntity shoppingCartEntity);
    ShoppingCartEntity toEntity(ShoppingCartDto shoppingCartDto);
    void updateShoppingCartFromDto(ShoppingCartDto dto, @MappingTarget ShoppingCartEntity entity);
}
