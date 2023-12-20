package com.example.cs393backend.RepositoryTests;

import com.example.cs393backend.entity.ProductEntity;
import com.example.cs393backend.entity.ReviewEntity;
import com.example.cs393backend.entity.UserEntity;
import com.example.cs393backend.repository.ProductRepository;
import com.example.cs393backend.repository.ReviewRepository;
import com.example.cs393backend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ReviewRepositoryTest {

    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    public void testCreateReview() {
        // Given
        ReviewEntity review = createReview();

        // When
        ReviewEntity savedReview = reviewRepository.save(review);

        // Then
        assertNotNull(savedReview);
        assertNotNull(savedReview.getId());
    }

    @Test
    public void testReadReview() {
        // Given
        ReviewEntity review = createReview();
        ReviewEntity savedReview = reviewRepository.save(review);

        // When
        Optional<ReviewEntity> foundReview = reviewRepository.findById(savedReview.getId());

        // Then
        assertTrue(foundReview.isPresent());
        assertEquals(savedReview.getId(), foundReview.get().getId());
        assertEquals(savedReview.getRating(), foundReview.get().getRating());
        assertEquals(savedReview.getComment(), foundReview.get().getComment());
    }

    @Test
    public void testUpdateReview() {
        // Given
        ReviewEntity review = createReview();
        ReviewEntity savedReview = reviewRepository.save(review);
        savedReview.setRating(5);
        savedReview.setComment("Updated comment");

        // When
        ReviewEntity updatedReview = reviewRepository.save(savedReview);

        // Then
        assertNotNull(updatedReview);
        assertEquals(5, updatedReview.getRating());
        assertEquals("Updated comment", updatedReview.getComment());
    }

    @Test
    public void testDeleteReview() {
        // Given
        ReviewEntity review = createReview();
        ReviewEntity savedReview = reviewRepository.save(review);

        // When
        reviewRepository.deleteById(savedReview.getId());

        // Then
        Optional<ReviewEntity> deletedReview = reviewRepository.findById(savedReview.getId());
        assertFalse(deletedReview.isPresent());
    }

    private ReviewEntity createReview() {
        // Create a user
        UserEntity user = new UserEntity();
        user.setUsername("testUser");
        user.setEmail("testUser@example.com");
        user = userRepository.save(user);

        // Create a product
        ProductEntity product = new ProductEntity();
        product.setName("Test Product");
        product.setDescription("This is a test product.");
        product.setPrice(100.0);
        product = productRepository.save(product);

        // Create a review
        ReviewEntity review = new ReviewEntity();
        review.setRating(5);
        review.setComment("Great product!");
        review.setUser(user);
        review.setProduct(product);

        return review;
    }


}
