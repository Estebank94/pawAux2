package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.SpecialtyDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
//import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public class SpecialtyDaoImpl implements SpecialtyDao {

    private final JdbcTemplate jdbcTemplate;
//    private SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<Integer> ROW_MAPPER = (rs, rowNum) -> new Integer(rs.getInt("id"));

    @Autowired
    public SpecialtyDaoImpl(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
    }

    @Override
    public Optional<List<Integer>> findSpecialtysId(Set<String> specialtySet) {
        if (specialtySet.size() == 0) {
            return Optional.empty();
        }

        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM specialty WHERE specialtyname in ");
        query.append(setToString(specialtySet));

        final List<Integer> list = jdbcTemplate.query(query.toString(), ROW_MAPPER);
        if (list.isEmpty()) {
            return Optional.empty();
        }
        return  Optional.of(list);
    }

    private String setToString(Set<String> setToString) {
        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        for (String stringIterator: setToString) {
            if (isFirst){
                sb.append("('");
                isFirst = false;
            } else {
                sb.append(",'");
            }
            sb.append(stringIterator);
            sb.append("'");
        }
        sb.append(")");
        return sb.toString();
    }
}