package com.example.cs393backend.service;

import com.example.cs393backend.dto.ReviewDto;
import com.example.cs393backend.entity.ReviewEntity;
import com.example.cs393backend.entity.UserEntity;
import com.example.cs393backend.entity.ProductEntity;
import com.example.cs393backend.util.ReviewMapper;
import com.example.cs393backend.repository.ReviewRepository;
import com.example.cs393backend.repository.UserRepository;
import com.example.cs393backend.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ReviewMapper reviewMapper;

    public ReviewService(ReviewRepository reviewRepository, UserRepository userRepository, ProductRepository productRepository, ReviewMapper reviewMapper) {
        this.reviewRepository = reviewRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.reviewMapper = reviewMapper;
    }

    public List<ReviewDto> findAll() {
        return reviewRepository.findAll().stream()
                .map(reviewMapper::reviewEntityToDto)
                .collect(Collectors.toList());
    }

    public ReviewDto findById(Long id) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));
        return reviewMapper.reviewEntityToDto(review);
    }

    public ReviewDto createReview(ReviewDto reviewDto) {
        ReviewEntity reviewEntity = reviewMapper.reviewDtoToEntity(reviewDto);

        // Set associated user and product
        setUserAndProduct(reviewEntity, reviewDto.getUserId(), reviewDto.getProductId());

        ReviewEntity savedReview = reviewRepository.save(reviewEntity);
        return reviewMapper.reviewEntityToDto(savedReview);
    }

    public ReviewDto updateReview(Long id, ReviewDto reviewDto) {
        ReviewEntity existingReview = reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Review not found"));

        // Update the properties
        existingReview.setRating(reviewDto.getRating());
        existingReview.setComment(reviewDto.getComment());

        // Set associated user and product
        setUserAndProduct(existingReview, reviewDto.getUserId(), reviewDto.getProductId());

        ReviewEntity updatedReview = reviewRepository.save(existingReview);
        return reviewMapper.reviewEntityToDto(updatedReview);
    }

    public void deleteReview(Long id) {
        if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("Review not found");
        }
        reviewRepository.deleteById(id);
    }

    private void setUserAndProduct(ReviewEntity reviewEntity, Long userId, Long productId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));
        reviewEntity.setUser(user);
        reviewEntity.setProduct(product);
    }
}
