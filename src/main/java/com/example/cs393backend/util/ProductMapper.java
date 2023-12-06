package com.example.cs393backend.util;

import com.example.AmazonBackendClone.dto.ProductDto;
import com.example.cs393backend.entity.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(ProductEntity productEntity);
    ProductEntity toEntity(ProductDto productDto);
    void updateProductFromDto(ProductDto dto, @MappingTarget ProductEntity entity);
}
