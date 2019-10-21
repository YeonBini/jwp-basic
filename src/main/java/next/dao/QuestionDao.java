package next.dao;

import core.exception.DataAccessException;
import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.Question;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class QuestionDao {

    public void insert(Question question) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "INSERT INTO QUESTIONS(writer, title, contents, createdDate) VALUES (?, ?, ?, ?) ";
        jdbcTemplate.update(sql,
                question.getWriter(), question.getTitle(), question.getContents(), new Timestamp(question.getTimeFromCreatedDate()));
    }

    public void update(Question question) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE QUESTIONS " +
                "SET writer=?, title=?, contents=?, createdDate=? " +
                "WHERE questionId=?";
        jdbcTemplate.update(sql,
                question.getWriter(), question.getTitle(), question.getContents(), new Timestamp(question.getTimeFromCreatedDate()), question.getQuestionId());
    }

    public Question findByQuestionId(long questionId) {
        RowMapper<Question> rowMapper = getRowMapper();

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS " +
                     "WHERE questionId=?";
        return jdbcTemplate.queryForObject(sql, rowMapper, questionId);
    }

    public List<Question> findAll() {
        RowMapper<Question> rowMapper = getRowMapper();

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS ";
        return jdbcTemplate.query(sql, rowMapper);
    }

    private RowMapper getRowMapper() {
        return res -> {
            try {
                return new Question(
                        res.getLong("questionId"),
                        res.getString("writer"),
                        res.getString("title"),
                        res.getString("contents"),
                        res.getTimestamp("createdDate"),
                        res.getInt("countOfAnswer")
                );
            } catch (SQLException e) {
                throw new DataAccessException(e.getMessage());
            }
        };
    }

}
