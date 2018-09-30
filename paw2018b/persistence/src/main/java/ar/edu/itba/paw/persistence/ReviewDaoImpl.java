package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ReviewDao;
import ar.edu.itba.paw.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ReviewDaoImpl implements ReviewDao {

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public ReviewDaoImpl(final DataSource ds){
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("review")
                .usingColumns("description","stars","doctorid"
                        ,"userId","daytime")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<Review> createReview(String description,Integer stars,
                               Integer doctorId, Integer userId) {
        final Map<String,Object> entry = new HashMap<>();
        LocalDateTime nowDateTime = LocalDateTime.now();
        entry.put("description",description);
        entry.put("stars",stars);
        entry.put("doctorId",doctorId);
        entry.put("userId",userId);
        entry.put("daytime",LocalDateTime.now().toString());
        final Number reviewId = jdbcInsert.executeAndReturnKey(entry);
        Review review  = new Review(stars, nowDateTime, description,new Integer(reviewId.intValue()));
        return Optional.ofNullable(review);
    }
}
