package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.Controller;
import core.mvc.ModelAndView;
import core.mvc.View;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.model.Result;
import next.view.JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteAnswerController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        AnswerDao answerDao = new AnswerDao();
        long answerId = Long.parseLong(req.getParameter("answerId"));
        Answer answer = answerDao.findById(answerId);
        boolean isDeleted = answerDao.delete(answerId);

        ModelAndView mav = jsonView();
        if(isDeleted) {
            mav.addObject("answer", Result.ok());
        } else {
            mav.addObject("answer", Result.fail("delete answer failed"));
        }

        QuestionDao questionDao = new QuestionDao();
        Question question = questionDao.findById(answer.getQuestionId());
        question.subtractCountofComment();
        questionDao.update(question);

        mav.addObject("question", question);

        return mav;
    }
}
