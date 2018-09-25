package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.ReviewDao;
import ar.edu.itba.paw.models.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ReviewDaoImpl implements ReviewDao {

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public ReviewDaoImpl(final DataSource ds){
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("review")
                .usingColumns("doctorid","reviewerId",
                        "datetime", "stars",
                        "review")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Review createReview(Integer stars, String description, Integer doctorId, Integer userId) {
        final Map<String,Object> entry = new HashMap<>();
        LocalDateTime nowDateTime = LocalDateTime.now();
        entry.put("doctorId",doctorId);
        entry.put("userId",userId);
        entry.put("stars",stars);
        entry.put("description",description);
        entry.put("datetime", nowDateTime);
        final Number reviewId = jdbcInsert.executeAndReturnKey(entry);
        return new Review(stars, nowDateTime, description,new Integer(reviewId.intValue()));
    }
}
