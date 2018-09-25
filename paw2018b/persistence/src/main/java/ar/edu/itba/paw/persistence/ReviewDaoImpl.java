package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ReviewDao;
import ar.edu.itba.paw.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.Optional;

public class ReviewDaoImpl implements ReviewDao {

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public AppointmentDaoImpl(final DataSource ds){
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("apploiment")
                .usingColumns("doctorid","reviewerId","datetime", "stars",
                        "review")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<Review> createReview(Integer stars, String review, Integer doctorId, Integer userId) {
        return Optional.empty();
    }
}
