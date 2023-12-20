package com.example.cs393backend.util;

import com.example.cs393backend.dto.ItemDto;
import com.example.cs393backend.entity.ItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    @Mapping(source = "product.id", target = "productId")
    @Mapping(source = "shoppingCart.id", target = "shoppingCartId")
    ItemDto itemEntityToDto(ItemEntity itemEntity);

    ItemEntity itemDtoToEntity(ItemDto itemDto);
}
