package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.Controller;
import core.mvc.ModelAndView;
import core.mvc.View;
import next.dao.AnswerDao;
import next.model.Result;
import next.view.JsonView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteAnswerController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        AnswerDao answerDao = new AnswerDao();
        boolean isDeleted = answerDao.delete(Long.parseLong(req.getParameter("answerId")));

        ModelAndView mav = jsonView();
        if(isDeleted) {
            mav.addObject("answer", Result.ok());
        } else {
            mav.addObject("answer", Result.fail("delete answer failed"));
        }
        return mav;
    }
}
