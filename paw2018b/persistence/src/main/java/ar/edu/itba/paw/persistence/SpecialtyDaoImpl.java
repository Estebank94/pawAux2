package ar.edu.itba.paw.persistence;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.Optional;

public class SpecialtyDaoImpl {

    private final JdbcTemplate jdbcTemplate;

    private static final RowMapper<Integer> ROW_MAPPER = (rs, rowNum) -> new Integer(rs.getInt("id"));

    @Override
    public Optional<Integer> findSpecialtyId(String specialty) {
        final Integer id = jdbcTemplate.query("SELECT * FROM specialty WHERE specialtyname = ?", specialty, ROW_MAPPER);

    }
}
