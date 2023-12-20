package com.example.cs393backend.util;

import com.example.cs393backend.dto.OrderDto;
import com.example.cs393backend.entity.OrderEntity;
import com.example.cs393backend.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "address.id", target = "addressId")
    @Mapping(source = "products", target = "productIds", qualifiedByName = "mapProductsToIds")
    OrderDto orderEntityToDto(OrderEntity orderEntity);

    @Named("mapProductsToIds")
    default List<Long> mapProductsToIds(List<ProductEntity> products) {
        if (products == null) {
            return null;
        }
        return products.stream().map(ProductEntity::getId).collect(Collectors.toList());
    }

    // Additional mappings as needed
}
