package next.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApiQuestionListController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();

        List<Map<String, Object>> questionList = new ArrayList<>();
        List<Question> questions = questionDao.findAll();
        ObjectMapper objectMapper = new ObjectMapper();
        for(Question q : questions) {
            long questionId = q.getQuestionId();
            Map<String, Object> map = objectMapper.convertValue(q, Map.class);
            List<Answer> answerList = answerDao.findAllByQuestionId(questionId);
            map.put("answers", answerList);
            questionList.add(map);
        }

        return jsonView().addObject("questions", questionList);
    }
}
