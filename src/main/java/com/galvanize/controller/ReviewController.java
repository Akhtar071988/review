package com.galvanize.controller;

import com.galvanize.entity.Review;
import com.galvanize.service.RestService;
import com.galvanize.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {
    ReviewService reviewService;
    RestService restService;
    RestTemplate restTemplate;
    private String apiKey = "&apikey=656a57f5";
    private String url = "http://omdbapi.com";

    public ReviewController(RestTemplate restTemplate, ReviewService reviewService, RestService restService) {
        this.reviewService = reviewService;
        this.restService = restService;
    }

    @PostMapping
    public ResponseEntity<Review> postReview(@RequestBody Review Post){
        if(!restService.validate(Post.getImdbId())){
            return ResponseEntity.ok(reviewService.postReview(Post));
        }
        return null;
    }

    @GetMapping("/title/{title}")
    public ResponseEntity<List<Review>> searchReviews(@PathVariable String title){
        String searchUrl = url + "?s=" + title + apiKey;
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/imdbId/{imdbId}")
    public ResponseEntity getOneMovieReviewByimdbId(@PathVariable String imdbId, Object title){
        return ResponseEntity.ok(reviewService.findReviewByImdbId(imdbId));
    }

    @PutMapping("/rating/{reviewId}")
    public Review updateMovieReviewWithStarRating(@PathVariable long reviewId, @RequestBody Review review){
        return reviewService.updateReviewById(reviewId, review);
    }


    @DeleteMapping("/{reviewId}")
    public boolean updateMovie(@PathVariable long reviewId){
        return reviewService.deleteById(reviewId);
    }
}
