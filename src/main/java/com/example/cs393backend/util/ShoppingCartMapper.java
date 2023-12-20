package com.example.cs393backend.util;

import com.example.cs393backend.dto.ShoppingCartDto;
import com.example.cs393backend.entity.ShoppingCartEntity;
import com.example.cs393backend.entity.ItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ShoppingCartMapper {
    @Mapping(source = "shoppingCart.user.id", target = "userId")
    @Mapping(source = "shoppingCart.items", target = "itemIds", qualifiedByName = "mapItemsToIds")
    ShoppingCartDto shoppingCartEntityToDto(ShoppingCartEntity shoppingCart);

    @Named("mapItemsToIds")
    default List<Long> mapItemsToIds(List<ItemEntity> items) {
        if (items == null) {
            return null;
        }
        return items.stream().map(ItemEntity::getId).collect(Collectors.toList());
    }

    // Additional mapping methods can be defined as needed
}
