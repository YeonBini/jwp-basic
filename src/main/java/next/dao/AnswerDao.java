package next.dao;

import core.exception.DataAccessException;
import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;
import next.model.Answer;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class AnswerDao {

    public void insert(Answer answer) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sqlForInsertAnswer = "INSERT INTO ANSWERS(writer, contents, createdDate, questionId) " +
                     "VALUES (?, ?, ?, ?) ";
        String sqlForUpdateQuestion = "UPDATE QUESTIONS " +
                                      "SET countOfAnswer = countOfAnswer + 1 " +
                                      "WHERE questionId = ?";
        jdbcTemplate.update(sqlForInsertAnswer, answer.getWriter(), answer.getContents(), new Timestamp(answer.getTimeForCreatedDate()), answer.getQuestionId());
        jdbcTemplate.update(sqlForUpdateQuestion, answer.getQuestionId());
    }

    public void update(Answer answer) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "UPDATE ANSWERS " +
                     "SET writer=?, contents=?, createdDate=? " +
                     "WHERE answerId=?";
        jdbcTemplate.update(sql, answer.getWriter(), answer.getContents(), new Timestamp(answer.getTimeForCreatedDate()), answer.getAnswerId());
    }

    public Answer findByAnswerId(long answerId) {
        RowMapper<Answer> rowMapper = getAnswerRowMapper();

        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT answerId, writer, contents, createdDate, questionId " +
                     "FROM ANSWERS " +
                     "WHERE answerId=?";

        return jdbcTemplate.queryForObject(sql, rowMapper, answerId);
    }

    public List<Answer> findAllByQuestionId(long questionId) {
        RowMapper<Answer> rowMapper = getAnswerRowMapper();
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT answerId, writer, contents, createdDate, questionId " +
                "FROM ANSWERS " +
                "WHERE questionId=?";

        return jdbcTemplate.query(sql, rowMapper, questionId);
    }



    public List<Answer> findAll() {
        RowMapper<Answer> rowMapper = getAnswerRowMapper();
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "SELECT answerId, writer, contents, createdDate, questionId " +
                "FROM ANSWERS ";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void delete(long answerId) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        String sql = "DELETE FROM ANSWERS " +
                     "WHERE answerId=?";
        jdbcTemplate.update(sql, answerId);
    }

    private RowMapper<Answer> getAnswerRowMapper() {
        return res -> {
            try {
                return new Answer(
                        res.getLong("answerId"),
                        res.getString("writer"),
                        res.getString("contents"),
                        res.getTimestamp("createdDate"),
                        res.getLong("questionId")
                );
            } catch (SQLException e) {
                throw new DataAccessException(e.getMessage());
            }
        };
    }

}
