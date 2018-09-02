package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.SearchDao;
import ar.edu.itba.paw.models.ListItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Created by estebankramer on 02/09/2018.
 */

@Repository
public class SearchDaoImpl implements SearchDao {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<ListItem> ROW_MAPPER = new RowMapper<ListItem>(){

        @Override
        public ListItem mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new ListItem( rs.getString("insuranceName"), rs.getInt("id"));
        }
    };

    @Autowired
    public SearchDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Optional<List<ListItem>> listInsurances() {

        final List<ListItem> list = jdbcTemplate.query("SELECT * FROM insurance", ROW_MAPPER);

        if(list.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(list);
    }

    @Override
    public Optional<List<ListItem>> listZones() {
        return null;
    }
}
