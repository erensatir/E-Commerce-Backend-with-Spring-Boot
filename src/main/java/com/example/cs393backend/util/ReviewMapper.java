package com.example.cs393backend.util;

import com.example.cs393backend.dto.ReviewDto;
import com.example.cs393backend.entity.ReviewEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    ReviewDto toDto(ReviewEntity reviewEntity);
    ReviewEntity toEntity(ReviewDto reviewDto);
    void updateReviewFromDto(ReviewDto dto, @MappingTarget ReviewEntity entity);
}
