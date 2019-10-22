package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.dao.QuestionDao;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddQuestionController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Question question = new Question(
                req.getParameter("writer"),
                req.getParameter("title"),
                req.getParameter("contents")
        );
        QuestionDao questionDao = new QuestionDao();
        Question questionReturned = questionDao.insert(question);
        if(questionReturned == null) {
            return null;
        }
        return jspView("redirect:/").addObject("questions", questionDao.findAll());
    }
}
