package com.galvanize.service;

import com.galvanize.entity.Review;
import com.galvanize.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import javax.persistence.Table;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ReviewServiceTest {

    @MockBean
    ReviewRepository reviewRepository;

    @Autowired
    ReviewService reviewService;

    @Test
    public void postReview(){
        Review review = new Review("xyz@xyz.com", "tt0241527", "Harry Potter and the Sorcerer's Stone", 4, "hello");
        Review expected = new Review(1L, "xyz@xyz.com", "tt0241527", "Harry Potter and the Sorcerer's Stone", 4, "hello");
        when(reviewRepository.save(any(Review.class))).thenReturn(expected);
        assertEquals(expected, reviewService.postReview(review));
    }

    @Test
    public void getAllReviews(){
        Review expected = new Review(1L, "xyz@xyz.com", "tt0241527", "Harry Potter and the Sorcerer's Stone", 4, "hello");
        ArrayList<Review> expectedReviews = new ArrayList<>();
        //String url = "imdb.com";
        expectedReviews.add(expected);
        when(reviewRepository.findAll()).thenReturn(expectedReviews);
        assertEquals(expectedReviews, reviewService.getAllReviews());
    }

    @Test
    public void getMovieByImdbId(){
        Review expected = new Review(1L, "xyz@xyz.com", "tt0241527", "Harry Potter and the Sorcerer's Stone", 4, "hello");
        when(reviewRepository.findReviewByImdbId(anyString())).thenReturn(expected);
        assertEquals(expected, reviewService.findReviewByImdbId("tt0241527"));
    }

    @Test
    public void updateMovieWithStarRating(){
        ReviewService reviewService = new ReviewService(reviewRepository);
        Review expected = reviewService.postReview(new Review(1L, "xyz@xyz.com", "tt0241527", "Harry Potter and the Sorcerer's Stone", 5, "hello"));
        when(reviewService.findReviewByImdbId(anyString())).thenReturn(expected);
        assertEquals(expected, reviewService.findReviewByImdbId("tt0241527"));
    }

    @Test
    public void deleteMovie(){
        //ReviewService reviewService = new ReviewService(reviewRepository);
        when(reviewRepository.deleteById(anyLong())).thenReturn(true);
        assertTrue(reviewService.deleteById(1L));
    }
}