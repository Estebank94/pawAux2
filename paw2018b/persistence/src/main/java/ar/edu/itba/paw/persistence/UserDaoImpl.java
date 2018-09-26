package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {

    private SimpleJdbcInsert jdbcInsert;
    private JdbcTemplate jdbcTemplate;

    private final static RowMapper<User> ROW_MAPPER = (rs, rowNum) ->
            new User(rs.getInt("id"), rs.getString("password"),
                    rs.getString("email"), rs.getString("role") );

    @Autowired
    public UserDaoImpl(final DataSource ds){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName("user")
                .usingColumns("id","password", "email", "role");
        }

    @Override
    public User createUser(Integer id, String password, String email, String role) {
        final Map<String, Object> args = new HashMap<>();
        args.put("id", id);
        args.put("email", email);
        args.put("password", password);
        args.put("role", role);
        jdbcInsert.execute(args);
        return new User(id, email, password, role);
    }

    @Override
    public Optional<User> findUserById(Integer id) {
        final List<User> list = jdbcTemplate.query("SELECT * FROM user WHERE id = ?", ROW_MAPPER, id);

        if (list.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(list.get(0));
    }

    @Override
    public Optional<User> findUserByEmail(String email) {

        final List<User> list = jdbcTemplate.query("SELECT * FROM user WHERE email = ?", ROW_MAPPER, email);

        if (list.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(list.get(0));
    }

}
