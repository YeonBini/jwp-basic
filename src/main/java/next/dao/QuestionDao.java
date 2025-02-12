package next.dao;

import java.sql.*;
import java.util.List;

import core.jdbc.KeyHolder;
import core.jdbc.PreparedStatementCreator;
import next.model.Question;
import core.jdbc.JdbcTemplate;
import core.jdbc.RowMapper;

public class QuestionDao {
    private JdbcTemplate jdbcTemplate = JdbcTemplate.getInstance();

    public Question insert(Question question) {
        String sql = "INSERT INTO QUESTIONS (writer, title, contents, createdDate, countOfAnswer) " +
                     "VALUES (?, ?, ?, ?, 0)";
        PreparedStatementCreator psc = new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, question.getWriter());
                pstmt.setString(2, question.getTitle());
                pstmt.setString(3, question.getContents());
                pstmt.setTimestamp(4, new Timestamp(question.getTimeFromCreateDate()));

                return pstmt;
            }
        };
        KeyHolder keyHolder = new KeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        return findById(keyHolder.getId());
    }

    public Question update(Question question) {
        String sql = "UPDATE QUESTIONS" +
                " SET writer=?, title=?, contents=?, countOfAnswer=? " +
                " WHERE questionId=?";
        jdbcTemplate.update(sql,
                question.getWriter(),
                question.getTitle(),
                question.getContents(),
                question.getCountOfComment(),
                question.getQuestionId());

        return findById(question.getQuestionId());
    }

    public List<Question> findAll() {
        String sql = "SELECT questionId, writer, title, createdDate, countOfAnswer FROM QUESTIONS "
                + "order by questionId desc";

        RowMapper<Question> rm = new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs) throws SQLException {
                return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"), null,
                        rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
            }

        };

        return jdbcTemplate.query(sql, rm);
    }

    public Question findById(long questionId) {
        String sql = "SELECT questionId, writer, title, contents, createdDate, countOfAnswer FROM QUESTIONS "
                + "WHERE questionId = ?";

        RowMapper<Question> rm = new RowMapper<Question>() {
            @Override
            public Question mapRow(ResultSet rs) throws SQLException {
                return new Question(rs.getLong("questionId"), rs.getString("writer"), rs.getString("title"),
                        rs.getString("contents"), rs.getTimestamp("createdDate"), rs.getInt("countOfAnswer"));
            }
        };

        return jdbcTemplate.queryForObject(sql, rm, questionId);
    }

    public boolean deleteQuestion(long questionId) {
        String sql = "DELETE FROM QUESTIONS " +
                     "WHERE questionId=?";
        jdbcTemplate.update(sql, questionId);

        return findById(questionId) == null;
    }
}
