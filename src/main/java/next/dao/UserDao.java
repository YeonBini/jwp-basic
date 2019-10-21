package next.dao;

import core.exception.DataAccessException;
import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public class UserDao {
//    private final Logger log = LoggerFactory.getLogger(UserDao.class);

    public void insert(User user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO USERS VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, user.getUserId(), user.getPassword(), user.getName(), user.getEmail());
    }

    public void update(User user) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE USERS SET password=?, name=?, email=? WHERE userId=?";
        jdbcTemplate.update(sql, user.getPassword(), user.getName(), user.getEmail(), user.getUserId());
    }


    public List<User> findAll() {
        RowMapper<User> rowMapper = getRowMapperForUser();

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT userId, password, name, email FROM USERS";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public User findByUserId(String userId) {
        RowMapper<User> rowMapper = getRowMapperForUser();

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT userId, password, name, email FROM USERS WHERE userid=?";
        return jdbcTemplate.queryForObject(sql, rowMapper, userId);
    }

    private RowMapper getRowMapperForUser() {
        return res -> {
            try {
                return new User(
                        res.getString("userId"),
                        res.getString("password"),
                        res.getString("name"),
                        res.getString("email")
                );
            } catch (SQLException e) {
                throw new DataAccessException(e.getMessage());
            }
        };
    }
}
