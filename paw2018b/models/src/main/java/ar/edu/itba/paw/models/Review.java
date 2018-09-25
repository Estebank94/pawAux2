package ar.edu.itba.paw.models;

import java.time.LocalDateTime;

public class Review {
    private Integer stars;
    private LocalDateTime dateTime;
    private String description;
    private Integer id;

    public Review(Integer stars, LocalDateTime dateTime, String description, Integer id) {
        this.stars = stars;
        this.dateTime = dateTime;
        this.description = description;
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

}
