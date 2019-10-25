package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.Controller;
import core.mvc.ModelAndView;
import core.mvc.View;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddAnswerController extends AbstractController {
    private static final Logger log = LoggerFactory.getLogger(AddAnswerController.class);

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        AnswerDao answerDao = new AnswerDao();
        Answer answer = new Answer(
                req.getParameter("writer"),
                req.getParameter("contents"),
                Long.parseLong(req.getParameter("questionId"))
        );
        log.debug("answer : {}", answer);
        Answer savedAnswer = answerDao.insert(answer);

        QuestionDao questionDao = new QuestionDao();
        Question question = questionDao.findById(Long.parseLong(req.getParameter("questionId")));
        question.addCountofComment();
        questionDao.update(question);

        return jsonView().addObject("answer", savedAnswer).addObject("question", question);
    }
}
