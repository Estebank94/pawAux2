package ar.edu.itba.paw.webapp.dto;

import ar.edu.itba.paw.models.Review;

import java.util.LinkedList;
import java.util.List;

public class ReviewListDTO {
    private List<ReviewDTO> reviews;

    public ReviewListDTO(){}

    public ReviewListDTO(List<Review> reviews){
        this.reviews = new LinkedList<>();
        for (Review review: reviews){
            this.reviews.add(new ReviewDTO(review));
        }
    }
}
