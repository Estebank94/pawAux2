package ar.edu.itba.paw.webapp.forms;

/**
 * Created by estebankramer on 02/11/2018.
 */
public class ReviewForm {
    String stars;
    String description;
    Integer apponintmentId;

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getApponintmentId() {
        return apponintmentId;
    }

    public void setApponintment(Integer apponintmentId) {
        this.apponintmentId = apponintmentId;
    }
}
