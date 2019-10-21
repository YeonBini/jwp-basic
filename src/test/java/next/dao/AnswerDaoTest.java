package next.dao;

import core.jdbc.ConnectionManager;
import next.model.Answer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class AnswerDaoTest {

    @Before
    public void setup() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("jwp.sql"));
        DatabasePopulatorUtils.execute(populator, ConnectionManager.getDataSource());
    }

    @Test
    public void crud() {
        Answer expect = new Answer(
                1,
                "정연빈",
                "he is not good",
                new Date(),
                1);
        AnswerDao answerDao = new AnswerDao();
        answerDao.insert(expect);
        Answer actual = answerDao.findByAnswerId(expect.getAnswerId());
        assertEquals(expect, actual);

        expect = new Answer(1,
                "정연빈123",
                "he is good man",
                new Date(),
                1);
        answerDao.update(expect);
        actual = answerDao.findByAnswerId(expect.getAnswerId());
        assertEquals(expect, actual);
    }

    @Test
    public void findAll() {
        AnswerDao answerDao = new AnswerDao();
        List<Answer> list = answerDao.findAll();
        for(Answer a : list) System.out.println(a.getAnswerId());
        assertEquals(list.size(), 5);
    }

    @Test
    public void deleteAnswerTest() {
        Answer expect = new Answer(
                "정연빈",
                "he is not good",
                new Date(),
                1);
        AnswerDao answerDao = new AnswerDao();
        answerDao.insert(expect);

        answerDao.delete(1);
        List<Answer> list = answerDao.findAll();
        assertEquals(5, list.size());
    }

    @Test
    public void findByQuestionId() {

    }

}