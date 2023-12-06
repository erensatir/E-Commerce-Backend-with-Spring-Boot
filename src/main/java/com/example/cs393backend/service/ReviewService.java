package com.example.cs393backend.service;

import com.example.cs393backend.dto.ReviewDto;
import com.example.cs393backend.entity.ReviewEntity;
import com.example.cs393backend.repository.ReviewRepository;
import com.example.cs393backend.util.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReviewMapper reviewMapper;

    public ReviewDto createReview(ReviewDto reviewDto) {
        ReviewEntity reviewEntity = reviewMapper.toEntity(reviewDto);
        reviewRepository.save(reviewEntity);
        return reviewMapper.toDto(reviewEntity);
    }

    public ReviewDto getReview(Long id) {
        ReviewEntity reviewEntity = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return reviewMapper.toDto(reviewEntity);
    }

    public ReviewDto updateReview(ReviewDto reviewDto) {
        ReviewEntity reviewEntity = reviewRepository.findById(reviewDto.getId())
                .orElseThrow(() -> new RuntimeException("Review not found"));
        reviewMapper.updateReviewFromDto(reviewDto, reviewEntity);
        reviewRepository.save(reviewEntity);
        return reviewMapper.toDto(reviewEntity);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }
}
