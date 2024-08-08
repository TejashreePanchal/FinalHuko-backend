package com.example.huko.mongoDB.services;

import com.example.huko.mongoDB.models.Show;
import com.example.huko.mongoDB.models.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    @Autowired
    private ShowRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Show> getShows() {
        return repository.findAll();
    }

    public void insertIntoShows(Show show) {
        repository.insert(show);
    }

    public Optional<Show> getShowById(String id) throws Exception {
        Optional<Show> show = repository.findById(id);
        if (!show.isPresent()) {
            throw new Exception("Show with " + id + " is not found");
        }
        return show;
    }

    public void deleteShow(String id) {
        repository.deleteById(id);
    }

    public List<Show> searchShowsByTitle(String title) {
        Query query = new Query();
        query.addCriteria(Criteria.where("title").regex(".*" + title + ".*", "i")); // Case insensitive search
        return mongoTemplate.find(query, Show.class);
    }

    public List<Show> getFeaturedShows(String featured) {
        Query query = new Query();
        query.addCriteria(Criteria.where("featured").is(featured));
        return mongoTemplate.find(query, Show.class);
    }

    public Show updateShow(String id, Show updatedShowData) throws Exception {
        Optional<Show> existingShowOptional = getShowById(id);
        if (!existingShowOptional.isPresent()) {
            throw new Exception("Show with id " + id + " is not found");
        }

        Show existingShow = existingShowOptional.get();

        if (updatedShowData.getTitle() != null) existingShow.setTitle(updatedShowData.getTitle());
        if (updatedShowData.getGenre() != null) existingShow.setGenre(updatedShowData.getGenre());
        if (updatedShowData.getDescription() != null) existingShow.setDescription(updatedShowData.getDescription());
        if (updatedShowData.getYear() != 0) existingShow.setYear(updatedShowData.getYear());
        if (updatedShowData.getImdb() != null) existingShow.setImdb(updatedShowData.getImdb());
        if (updatedShowData.getDuration() != null) existingShow.setDuration(updatedShowData.getDuration());
        if (updatedShowData.getImage() != null) existingShow.setImage(updatedShowData.getImage());
        if (updatedShowData.getRentPrice() != null) existingShow.setRentPrice(updatedShowData.getRentPrice());
        if (updatedShowData.getBuyPrice() != null) existingShow.setBuyPrice(updatedShowData.getBuyPrice());
        if (updatedShowData.getContentType() != null) existingShow.setContentType(updatedShowData.getContentType());
        if (updatedShowData.getFeatured() != null) existingShow.setFeatured(updatedShowData.getFeatured());

        repository.save(existingShow);
        return existingShow;
    }
}
