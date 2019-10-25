package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiQuestionUpdateController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        long questionId = Long.parseLong(req.getParameter("questionId"));
        QuestionDao questionDao = new QuestionDao();
        Question question = questionDao.findById(questionId);

        Question updateQuestion = new Question(
                questionId,
                req.getParameter("writer"),
                req.getParameter("title"),
                req.getParameter("contents"),
                question.getCreatedDate(),
                question.getCountOfComment()
        );
        Question questionReturned = questionDao.update(updateQuestion);

        if(questionReturned == null) {
            return null;
        }
        return jspView("redirect:/qna/show?questionId=" + questionId);
//        return jspView("redirect:/").addObject("questions", questionDao.findAll());
    }
}
