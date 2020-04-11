package com.galvanize.service;

import com.galvanize.entity.Movie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {
    final String baseURL = "http://localhost:8081";
    RestTemplate restTemplate;

    public RestService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public boolean validate(String imdbId){
        String url = baseURL + "/api/movies/imdbId/" + imdbId;
        ResponseEntity<Movie> returnValue = restTemplate.getForEntity(url, Movie.class);
        boolean valid = returnValue.getBody() != null;
        return valid;
    }

    public Movie getMovieInfo(String imdbId) {
        String url = baseURL + "/api/movies/imdbId/" + imdbId;
        return (Movie) restTemplate.getForEntity(url, Model.class).getBody();
    }
}
