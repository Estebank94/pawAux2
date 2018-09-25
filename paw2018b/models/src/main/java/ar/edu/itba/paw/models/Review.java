package ar.edu.itba.paw.models;

import java.time.LocalDateTime;

public class Review {
    private Integer stars;
    private LocalDateTime dateTime;
    private String review;

    public Review(Integer stars, LocalDateTime dateTime, String review) {
        this.stars = stars;
        this.dateTime = dateTime;
        this.review = review;
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

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }
}
