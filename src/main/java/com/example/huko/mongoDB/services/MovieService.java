package com.example.huko.mongoDB.services;

import com.example.huko.mongoDB.models.Movie;
import com.example.huko.mongoDB.models.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Movie> getMovies() {
        return repository.findAll();
    }

    public void insertIntoMovies(Movie movie) {
        repository.insert(movie);
    }

    public Optional<Movie> getMovieById(String id) throws Exception {
        Optional<Movie> movie = repository.findById(id);
        if (!movie.isPresent()) {
            throw new Exception("Movie with " + id + " is not found");
        }
        return movie;
    }

    public void deleteMovie(String id) {
        repository.deleteById(id);
    }

    //mongoTemplate.find(query, Movie.class): Uses mongoTemplate to execute the query and find movies matching the criteria.

    public List<Movie> searchMoviesByTitle(String title) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").regex(".*" + title + ".*", "i")); // Case insensitive search
        return mongoTemplate.find(query, Movie.class);
    }

    public List<Movie> getFeaturedMovies(String featured) {
        Query query = new Query();
        query.addCriteria(Criteria.where("featured").is(featured));
        return mongoTemplate.find(query, Movie.class);
    }
    //optional - his is a container object used in Java that may or may not contain a non-null value
    public Movie updateMovie(String id, Movie updatedMovieData) throws Exception {
        Optional<Movie> existingMovieOptional = getMovieById(id);
        if (!existingMovieOptional.isPresent()) {
            throw new Exception("Movie with id " + id + " is not found");
        }

        //Retrieves the existing movie.
        Movie existingMovie = existingMovieOptional.get();

        //updatedMovieData: This is an object containing the new data for the movie.
        if (updatedMovieData.getTitle() != null) existingMovie.setTitle(updatedMovieData.getTitle());
        if (updatedMovieData.getGenre() != null) existingMovie.setGenre(updatedMovieData.getGenre());
        if (updatedMovieData.getDescription() != null) existingMovie.setDescription(updatedMovieData.getDescription());
        if (updatedMovieData.getYear() != 0) existingMovie.setYear(updatedMovieData.getYear());
        if (updatedMovieData.getImdb() != null) existingMovie.setImdb(updatedMovieData.getImdb());
        if (updatedMovieData.getDuration() != null) existingMovie.setDuration(updatedMovieData.getDuration());
        if (updatedMovieData.getImage() != null) existingMovie.setImage(updatedMovieData.getImage());
        if (updatedMovieData.getRentPrice() != null) existingMovie.setRentPrice(updatedMovieData.getRentPrice());
        if (updatedMovieData.getBuyPrice() != null) existingMovie.setBuyPrice(updatedMovieData.getBuyPrice());
        if (updatedMovieData.getContentType() != null) existingMovie.setContentType(updatedMovieData.getContentType());
        if (updatedMovieData.getFeatured() != null) existingMovie.setFeatured(updatedMovieData.getFeatured());

        repository.save(existingMovie);
        return existingMovie;
    }
}
