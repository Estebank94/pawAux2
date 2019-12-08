package ar.edu.itba.paw.webapp.dto.reviews;

import ar.edu.itba.paw.models.Review;

public class ReviewDTO {
    private Integer stars;
    private String dayTime;
    private String description;
    private String reviewerFirstName;
    private String reviewerLastName;

    public ReviewDTO(){}

    public ReviewDTO(Review review){
        this.stars = review.getStars();
        this.dayTime = review.getDayTime();
        this.description = review.getDescription();
        this.reviewerFirstName = review.getReviewerFirstName();
        this.reviewerLastName = review.getReviewerLastName();
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getDayTime() {
        return dayTime;
    }

    public void setDayTime(String dayTime) {
        this.dayTime = dayTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReviewerFirstName() {
        return reviewerFirstName;
    }

    public void setReviewerFirstName(String reviewerFirstName) {
        this.reviewerFirstName = reviewerFirstName;
    }

    public String getReviewerLastName() {
        return reviewerLastName;
    }

    public void setReviewerLastName(String reviewerLastName) {
        this.reviewerLastName = reviewerLastName;
    }
}
