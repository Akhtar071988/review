package com.galvanize.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.galvanize.entity.Review;
import com.galvanize.service.RestService;
import com.galvanize.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ReviewControllerTest {
    @Autowired
    MockMvc mvc;

    @MockBean
    ReviewService reviewService;

    @MockBean
    RestService restService;
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void postReview() throws Exception {
        Review expected = new Review();
        expected.setReviewId(1L);
        expected.setImdbId("tt0241527");
        String json = objectMapper.writeValueAsString(expected);
        when(restService.validate(anyString())).thenReturn(false);
        when(reviewService.postReview(any(Review.class))).thenReturn(expected);
        mvc.perform(post("/api/reviews").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewId").value(expected.getReviewId()));
    }

    @Test
    public void getAllReviews() throws Exception {
        Review expected = new Review();
        expected.setImdbId("tt0241527");
        ArrayList<Review> review = new ArrayList<>();
        review.add(expected);
        when(restService.validate(anyString())).thenReturn(true);
        when(reviewService.getAllReviews()).thenReturn(review);
        mvc.perform(get("/api/reviews/title/Harry Potter"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].rating").value(expected.getRating()))
                .andExpect(jsonPath("$[0].imdbId").value(expected.getImdbId()))
                .andExpect(jsonPath("$[0].reviewId").value(expected.getReviewId()));
    }

    @Test
    public void getMovieReviewsByimdbId() throws Exception {
        Review expected = new Review();
        expected.setReviewId(1L);
        when(reviewService.findReviewByImdbId("tt0241527")).thenReturn(expected);
        mvc.perform(get("/api/reviews/imdbId/tt0241527"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.imdbId").value(expected.getImdbId()))
                .andExpect(jsonPath("$.reviewId").value(expected.getReviewId()));
    }


    @Test
    public void updateMovie() throws Exception {
        Review expected = new Review();
        expected.setReviewId(1L);
        String json = objectMapper.writeValueAsString(expected);
        when(reviewService.updateReviewById(anyLong(), any(Review.class))).thenReturn(expected);
        mvc.perform(put("/api/reviews/rating/1").content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.reviewId").value(expected.getReviewId()));
    }

    @Test
    public void deleteReviewById() throws Exception {
        Review expected = new Review();
        long reviewId = 1L;
        String title = "Harry Potter and the Sorcerer's Stone";
        String json = objectMapper.writeValueAsString(title);
        when(reviewService.deleteById(anyLong(), anyString())).thenReturn(true);
        mvc.perform(delete("/api/reviews/" + reviewId).content(json).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(false));
    }
}
