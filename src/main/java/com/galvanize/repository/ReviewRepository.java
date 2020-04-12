package com.galvanize.repository;

import com.galvanize.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.xml.bind.JAXBPermission;

public interface ReviewRepository extends JpaRepository <Review, Long>{
    Review findReviewByImdbId(String imdbId);
    //Movie findTwoMovieReviewsByImdbId(String imdbId);
    Review findAllMoviesByTitle(String title);
    Integer deleteById(long reviewId);
}
