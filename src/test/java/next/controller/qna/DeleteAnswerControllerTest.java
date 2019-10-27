package next.controller.qna;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import next.model.Result;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeleteAnswerControllerTest {

    @Test
    public void resultToJsonTest() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.printf(objectMapper.writeValueAsString(Result.ok()));
    }

}