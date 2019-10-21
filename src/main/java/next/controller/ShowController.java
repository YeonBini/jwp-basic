package next.controller;

import core.mvc.Controller;
import next.dao.AnswerDao;
import next.dao.QuestionDao;
import next.dao.UserDao;
import next.model.Answer;
import next.model.Question;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowController implements Controller {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // 1. 로그인 여부
//        UserSessionUtils.isLogined(req.getSession())

        // 2. 해당 questionId 파라미터 확인
        String param = req.getParameter("questionId");

        if(param == null) {
            return "redirect:/";
        }

        QuestionDao questionDao = new QuestionDao();
        AnswerDao answerDao = new AnswerDao();

        Question question = questionDao.findByQuestionId(Long.parseLong(param));
        List<Answer> answerList = answerDao.findAllByQuestionId(question.getQuestionId());

        req.setAttribute("question", question);
        req.setAttribute("answers", answerList);

        return "/qna/show.jsp";
    }
}
