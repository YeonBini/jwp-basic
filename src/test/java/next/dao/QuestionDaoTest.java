package next.dao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.jdbc.JdbcTemplate;
import next.model.Question;
import org.junit.Test;

import java.util.Map;

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

    @Test
    public void objectMapperTest() throws JsonProcessingException {
        Question question0 = new Question("정연빈", "정연빈인가?", "정연빈이다.");
        Question question1 = new Question("정연빈", "정연빈인가?", "정연빈이다.");
        QuestionDao questionDao = new QuestionDao();
        question0 = questionDao.insert(question0);
        question1 = questionDao.insert(question1);
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> objectMap = objectMapper.convertValue(question1, Map.class);
        System.out.println(objectMap.toString());
        System.out.println(objectMap.get("questionId").toString());

    }
}