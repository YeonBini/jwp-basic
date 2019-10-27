package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;
import next.dao.QuestionDao;
import next.model.Question;
import next.model.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateQuestionForm extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if(!UserSessionUtils.isLogined(req.getSession())) {
            return jspView("redirect:/users/loginForm");
        }
        User user = UserSessionUtils.getUserFromSession(req.getSession());

        QuestionDao questionDao = new QuestionDao();
        Question question = questionDao.findById(Long.parseLong(req.getParameter("questionId")));

        if(!user.getName().equals(question.getWriter())) {
            return jspView("redirect:/qna/show?questionId="+question.getQuestionId());
        }

        return jspView("/qna/update.jsp")
                .addObject("questionId", question.getQuestionId())
                .addObject("name", question.getWriter())
                .addObject("title", question.getTitle())
                .addObject("contents", question.getContents());
    }
}
