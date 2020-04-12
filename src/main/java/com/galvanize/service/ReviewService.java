package com.galvanize.service;

import com.galvanize.entity.Review;
import com.galvanize.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }

    public Review postReview(Review review) {
        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews(){
        return reviewRepository.findAll();
    }

    public Review findReviewByImdbId(String imdbId) {
        return reviewRepository.findReviewByImdbId(imdbId);
    }

    public Review updateReviewById(Long reviewId, Review review) {
        if(reviewRepository.existsById(reviewId)){
            return reviewRepository.save(review);
        }else {
            return null;
        }
    }

    public boolean deleteById(long reviewId, String title){
        if (!reviewRepository.existsById(reviewId)) {
            return false;
        }
        if(!title.equals(reviewRepository.findById(reviewId).get().getTitle())) {
            return false;
        }
        return reviewRepository.deleteById(reviewId) == 1;
    }
}