package next.dao;

import core.jdbc.ConnectionManager;
import next.model.Question;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class QuestionDaoTest {

    @Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void crud() {
        Question expect = new Question(
                1L,
                "연빈",
                "where is yeonbn",
                "I don't know where he is",
                new Date()
                );

        QuestionDao questionDao = new QuestionDao();
        questionDao.insert(expect);
        Question actual = questionDao.findByQuestionId(expect.getQuestionId());
        assertEquals(expect, actual);

        expect = new Question(1L,
                "정연빈",
                "where is 정연",
                "I don't know where he is~~",
                new Date());
        questionDao.update(expect);
        actual = questionDao.findByQuestionId(expect.getQuestionId());
        assertEquals(expect, actual);
    }

    @Test
    public void findAll() {
        Question expect = new Question(
                1L,
                "연빈",
                "where is yeonbn",
                "I don't know where he is",
                new Date()
        );

        QuestionDao questionDao = new QuestionDao();
        questionDao.insert(expect);

        List<Question> list = questionDao.findAll();
        assertEquals(9, list.size());
    }

}