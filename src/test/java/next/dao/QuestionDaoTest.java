package next.dao;

import core.jdbc.JdbcTemplate;
import next.model.Question;
import org.junit.Test;

import static org.junit.Assert.*;

public class QuestionDaoTest {

    @Test
    public void insertTest() {
        // given
        Question question = new Question("정연빈", "정연빈인가?", "정연빈이다.");
        QuestionDao questionDao = new QuestionDao();

        //when
        Question insertedQuestion = questionDao.insert(question);

        //then
        System.out.printf(insertedQuestion.toString());
        assertNotNull(insertedQuestion);

    }

}