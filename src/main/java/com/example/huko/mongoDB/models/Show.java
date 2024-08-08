package com.example.huko.mongoDB.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("shows")
public class Show {

    @Id
    private String id;
    private String title;
    private String genre;
    private String description;
    private int year;
    private Double imdb;
    private String duration;
    private String image;
    private Double rentPrice;
    private Double buyPrice;
    private String contentType; // Field for content type (movie or TV show)
    private String featured;

    public Show() {
    }

    public Show(String id, String title, String genre, String description, int year, Double imdb, String duration, String image, Double rentPrice, Double buyPrice, String contentType, String featured) {
        this.id = id;
        this.title = title;
        this.genre = genre;
        this.description = description;
        this.year = year;
        this.imdb = imdb;
        this.duration = duration;
        this.image = image;
        this.rentPrice = rentPrice;
        this.buyPrice = buyPrice;
        this.contentType = contentType;
        this.featured = featured;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Double getImdb() {
        return imdb;
    }

    public void setImdb(Double imdb) {
        this.imdb = imdb;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Double getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(Double rentPrice) {
        this.rentPrice = rentPrice;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getFeatured() {
        return featured;
    }

    public void setFeatured(String featured) {
        this.featured = featured;
    }

    @Override
    public String toString() {
        return "Show{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", description='" + description + '\'' +
                ", year=" + year +
                ", imdb=" + imdb +
                ", duration='" + duration + '\'' +
                ", image='" + image + '\'' +
                ", rentPrice=" + rentPrice +
                ", buyPrice=" + buyPrice +
                ", contentType='" + contentType + '\'' +
                ", featured='" + featured + '\'' +
                '}';
    }
}
