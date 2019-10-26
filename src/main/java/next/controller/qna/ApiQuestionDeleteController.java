package next.controller.qna;

import core.jdbc.CannotDeleteException;
import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.model.Result;
import next.service.QuestionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiQuestionDeleteController extends AbstractController {
    private QuestionService questionService = QuestionService.getInstance();

    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if(!UserSessionUtils.isLogined(req.getSession())) {
            return jsonView().addObject("errorMessage", "Not Logined");
        }

        long questionId = Long.parseLong(req.getParameter("questionId"));
        try {
            questionService.deleteQuestion(questionId, UserSessionUtils.getUserFromSession(req.getSession()));
            return jsonView().addObject("result", Result.ok());
        } catch (CannotDeleteException e) {
            return jsonView()
                    .addObject("question", questionService.findById(questionId))
                    .addObject("answers", questionService.findAllByQuestionId(questionId))
                    .addObject("errorMessage", e.getMessage());
        }
    }
}
