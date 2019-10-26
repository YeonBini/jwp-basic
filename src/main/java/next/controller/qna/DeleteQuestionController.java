package next.controller.qna;

import core.jdbc.CannotDeleteException;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.model.Answer;
import next.model.Question;
import next.service.QuestionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class DeleteQuestionController extends AbstractController {
    private QuestionService questionService = QuestionService.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if(!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/user/loginForm");
        }

        long questionId = Long.parseLong(req.getParameter("questionId"));
        try {
            questionService.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(req.getSession()));
            return jspView( "redirect:/");
        } catch (CannotDeleteException e) {
            return jspView("show.jsp")
                    .addObject("question", questionService.findById(questionId))
                    .addObject("answers", questionService.findAllByQuestionId(questionId))
                    .addObject("errorMessage", e.getMessage());
        }

    }
}
