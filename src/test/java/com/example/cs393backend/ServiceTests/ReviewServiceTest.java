
package com.example.cs393backend.ServiceTests;

import com.example.cs393backend.dto.ReviewDto;
import com.example.cs393backend.entity.ReviewEntity;
import com.example.cs393backend.entity.UserEntity;
import com.example.cs393backend.entity.ProductEntity;
import com.example.cs393backend.repository.ReviewRepository;
import com.example.cs393backend.repository.UserRepository;
import com.example.cs393backend.repository.ProductRepository;
import com.example.cs393backend.service.ReviewService;
import com.example.cs393backend.util.ReviewMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ReviewMapper reviewMapper;

    @InjectMocks
    private ReviewService reviewService;

    private ReviewDto reviewDto;
    private ReviewEntity reviewEntity;

    @BeforeEach
    void setUp() {
        reviewDto = new ReviewDto();
        reviewDto.setId(1L);
        reviewDto.setUserId(1L);
        reviewDto.setProductId(1L);
        reviewDto.setRating(5);
        reviewDto.setComment("Great product");

        reviewEntity = new ReviewEntity();
        reviewEntity.setId(1L);
        reviewEntity.setUser(new UserEntity());
        reviewEntity.setProduct(new ProductEntity());
        reviewEntity.setRating(5);
        reviewEntity.setComment("Great product");
    }

    @Test
    void findAllReviews() {
        when(reviewRepository.findAll()).thenReturn(Collections.singletonList(reviewEntity));
        when(reviewMapper.reviewEntityToDto(any(ReviewEntity.class))).thenReturn(reviewDto);

        List<ReviewDto> result = reviewService.findAll();

        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(reviewDto, result.get(0));

        verify(reviewRepository).findAll();
        verify(reviewMapper).reviewEntityToDto(any(ReviewEntity.class));
    }

    @Test
    void findReviewById() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(reviewEntity));
        when(reviewMapper.reviewEntityToDto(any(ReviewEntity.class))).thenReturn(reviewDto);

        ReviewDto result = reviewService.findById(1L);

        assertNotNull(result);
        assertEquals(reviewDto, result);

        verify(reviewRepository).findById(anyLong());
        verify(reviewMapper).reviewEntityToDto(any(ReviewEntity.class));
    }

    @Test
    void createReview() {
        when(reviewRepository.save(any(ReviewEntity.class))).thenReturn(reviewEntity);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(new UserEntity()));
        when(productRepository.findById(anyLong())).thenReturn(Optional.of(new ProductEntity()));
        when(reviewMapper.reviewDtoToEntity(any(ReviewDto.class))).thenReturn(reviewEntity);
        when(reviewMapper.reviewEntityToDto(any(ReviewEntity.class))).thenReturn(reviewDto);

        ReviewDto result = reviewService.createReview(reviewDto);

        assertNotNull(result);
        assertEquals(reviewDto, result);

        verify(reviewRepository).save(any(ReviewEntity.class));
        verify(userRepository).findById(anyLong());
        verify(productRepository).findById(anyLong());
        verify(reviewMapper).reviewDtoToEntity(any(ReviewDto.class));
        verify(reviewMapper).reviewEntityToDto(any(ReviewEntity.class));
    }

    @Test
    void updateReview() {
        when(reviewRepository.findById(anyLong())).thenReturn(Optional.of(reviewEntity));
        when(reviewRepository.save(any(ReviewEntity.class))).thenReturn(reviewEntity);
        when(userRepository.findById(any())).thenReturn(Optional.of(new UserEntity()));
        when(productRepository.findById(any())).thenReturn(Optional.of(new ProductEntity()));


        ReviewDto updatedReviewDto = new ReviewDto();
        updatedReviewDto.setId(1L);
        updatedReviewDto.setRating(4);
        updatedReviewDto.setComment("Updated Comment");

        when(reviewMapper.reviewEntityToDto(any(ReviewEntity.class))).thenReturn(updatedReviewDto);
        ReviewDto result = reviewService.updateReview(1L, updatedReviewDto);

        assertNotNull(result);
        assertEquals(updatedReviewDto.getRating(), result.getRating());
        assertEquals(updatedReviewDto.getComment(), result.getComment());

        verify(reviewRepository).findById(anyLong());
        verify(reviewRepository).save(any(ReviewEntity.class));
        verify(userRepository).findById(any());
        verify(productRepository).findById(any());
        verify(reviewMapper).reviewEntityToDto(any(ReviewEntity.class));
    }

    @Test
    void deleteReview() {
        Long reviewId = 1L;
        when(reviewRepository.existsById(reviewId)).thenReturn(true);
        doNothing().when(reviewRepository).deleteById(anyLong());

        reviewService.deleteReview(reviewId);

        verify(reviewRepository).existsById(reviewId);
        verify(reviewRepository).deleteById(reviewId);
    }
}