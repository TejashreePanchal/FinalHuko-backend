package com.example.huko.mongoDB.controllers;

import com.example.huko.mongoDB.CustomizedResponse;
import com.example.huko.mongoDB.models.Show;
import com.example.huko.mongoDB.services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/shows")
public class ShowController {

    private final ShowService service;

    @Autowired
    public ShowController(ShowService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<CustomizedResponse<Show>> getAllShows() {
        var customizedResponse = new CustomizedResponse<Show>("A List of Shows", service.getShows());
        return new ResponseEntity<>(customizedResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomizedResponse<Show>> getShowById(@PathVariable("id") String id) {
        CustomizedResponse<Show> customizedResponse;
        try {
            customizedResponse = new CustomizedResponse<Show>("Show with id " + id, Collections.singletonList(service.getShowById(id).orElse(null)));
        } catch (Exception e) {
            customizedResponse = new CustomizedResponse<Show>(e.getMessage(), null);
            return new ResponseEntity<>(customizedResponse, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(customizedResponse, HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<CustomizedResponse<Show>> addShow(@RequestBody Show show) {
        service.insertIntoShows(show);
        var response = new CustomizedResponse<Show>("Show added", Collections.singletonList(show));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomizedResponse<Show>> deleteShow(@PathVariable("id") String id) {
        service.deleteShow(id);
        var response = new CustomizedResponse<Show>("Show deleted", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<CustomizedResponse<Show>> searchShowsByTitle(@RequestParam(value = "title") String title) {
        var customizedResponse = new CustomizedResponse<Show>("A List of Shows with title containing: " + title, service.searchShowsByTitle(title));
        return new ResponseEntity<>(customizedResponse, HttpStatus.OK);
    }

    @GetMapping("/featured")
    public ResponseEntity<CustomizedResponse<Show>> getFeaturedShows(@RequestParam(value = "featured") String featured) {
        var customizedResponse = new CustomizedResponse<Show>("A List of Featured Shows", service.getFeaturedShows(featured));
        return new ResponseEntity<>(customizedResponse, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomizedResponse<Show>> updateShow(@PathVariable("id") String id, @RequestBody Show show) {
        CustomizedResponse<Show> customizedResponse;
        try {
            Optional<Show> existingShow = service.getShowById(id);
            if (!existingShow.isPresent()) {
                customizedResponse = new CustomizedResponse<Show>("Show with id " + id + " is not found", null);
                return new ResponseEntity<>(customizedResponse, HttpStatus.NOT_FOUND);
            }

            Show updatedShow = service.updateShow(id, show);
            customizedResponse = new CustomizedResponse<Show>("Show with id " + id + " is updated", Collections.singletonList(updatedShow));
            return new ResponseEntity<>(customizedResponse, HttpStatus.OK);
        } catch (Exception e) {
            customizedResponse = new CustomizedResponse<Show>(e.getMessage(), null);
            return new ResponseEntity<>(customizedResponse, HttpStatus.BAD_REQUEST);
        }
    }
}
