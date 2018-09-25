package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.interfaces.UserDao;
import ar.edu.itba.paw.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class UserDaoImpl implements UserDao {

    private SimpleJdbcInsert jdbcInsert;

    @Autowired
    public UserDaoImpl(final DataSource ds){
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

}
