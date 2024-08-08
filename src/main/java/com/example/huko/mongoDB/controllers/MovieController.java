package com.example.huko.mongoDB.controllers;

import com.example.huko.mongoDB.CustomizedResponse;
import com.example.huko.mongoDB.models.Movie;
import com.example.huko.mongoDB.services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService service;

    @Autowired
    public MovieController(MovieService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<CustomizedResponse<Movie>> getAllMovies() {
        var customizedResponse = new CustomizedResponse<Movie>("A List of Movies", service.getMovies());
        return new ResponseEntity<>(customizedResponse, HttpStatus.OK);
    }

    //@PathVariable("id"): This annotation extracts the id variable from the URL.
    //Collections.singletonList(...): Creates a list containing the result (either the movie or null).

    @GetMapping("/{id}")
    public ResponseEntity<CustomizedResponse<Movie>> getMovieById(@PathVariable("id") String id) {
        CustomizedResponse<Movie> customizedResponse;
        try {
            customizedResponse = new CustomizedResponse<Movie>("Movie with id " + id, Collections.singletonList(service.getMovieById(id).orElse(null)));
        } catch (Exception e) {
            customizedResponse = new CustomizedResponse<Movie>(e.getMessage(), null);
            return new ResponseEntity<>(customizedResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customizedResponse, HttpStatus.OK);
    }

    //MediaType.APPLICATION_JSON_VALUE}): This annotation maps HTTP POST requests to /movies and specifies that the request body should be in JSON format.
    //@RequestBody Movie movie: This annotation binds the request body to the movie parameter.

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CustomizedResponse<Movie>> addMovie(@RequestBody Movie movie) {
        service.insertIntoMovies(movie);
        var response = new CustomizedResponse<Movie>("Movie added", Collections.singletonList(movie));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //String id: This is the method parameter that will hold the value of the movie ID passed in the URL
    @DeleteMapping("/{id}")
    public ResponseEntity<CustomizedResponse<Movie>> deleteMovie(@PathVariable("id") String id) {
        service.deleteMovie(id);
        var response = new CustomizedResponse<Movie>("Movie deleted", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<CustomizedResponse<Movie>> searchMoviesByTitle(@RequestParam(value = "title") String title) {
        var customizedResponse = new CustomizedResponse<>("A List of Movies/TV Shows with title containing: " + title, service.searchMoviesByTitle(title));
        return new ResponseEntity<>(customizedResponse, HttpStatus.OK);
    }

    @GetMapping("/featured")
    public ResponseEntity<CustomizedResponse<Movie>> getFeaturedMovies(@RequestParam(value = "featured") String featured) {
        var customizedResponse = new CustomizedResponse<Movie>("A List of Featured Movies", service.getFeaturedMovies(featured));
        return new ResponseEntity<>(customizedResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomizedResponse<Movie>> updateMovie(@PathVariable("id") String id, @RequestBody Movie movie) {
        CustomizedResponse<Movie> customizedResponse;
        try {
            Optional<Movie> existingMovie = service.getMovieById(id);
            if (!existingMovie.isPresent()) {
                customizedResponse = new CustomizedResponse<Movie>("Movie with id " + id + " is not found", null);
                return new ResponseEntity<>(customizedResponse, HttpStatus.NOT_FOUND);
            }

            Movie updatedMovie = service.updateMovie(id, movie);
            customizedResponse = new CustomizedResponse<Movie>("Movie with id " + id + " is updated", Collections.singletonList(updatedMovie));
            return new ResponseEntity<>(customizedResponse, HttpStatus.OK);
        } catch (Exception e) {
            customizedResponse = new CustomizedResponse<Movie>(e.getMessage(), null);
            return new ResponseEntity<>(customizedResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
