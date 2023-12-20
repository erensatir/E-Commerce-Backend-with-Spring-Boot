package com.example.cs393backend.util;

import com.example.cs393backend.dto.ProductDto;
import com.example.cs393backend.entity.ProductEntity;
import com.example.cs393backend.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(source = "reviews", target = "reviewIds", qualifiedByName = "mapReviewsToIds")
    ProductDto productEntityToDto(ProductEntity productEntity);

    ProductEntity productDtoToEntity(ProductDto productDto);

    @Named("mapReviewsToIds")
    default List<Long> mapReviewsToIds(List<ReviewEntity> reviews) {
        if (reviews == null) {
            return null;
        }
        return reviews.stream().map(ReviewEntity::getId).collect(Collectors.toList());
    }
}
