package com.example.cs393backend.util;

import com.example.cs393backend.dto.ReviewDto;
import com.example.cs393backend.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "product.id", target = "productId")
    ReviewDto reviewEntityToDto(ReviewEntity reviewEntity);

    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "product.id", source = "productId")
    @Mapping(target = "user", ignore = true) // Exclude full UserEntity in the mapping
    @Mapping(target = "product", ignore = true) // Exclude full ProductEntity in the mapping
    ReviewEntity reviewDtoToEntity(ReviewDto reviewDto);
}
