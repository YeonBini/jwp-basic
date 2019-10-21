package next.controller;

import core.mvc.Controller;
import next.dao.QuestionDao;
import next.dao.UserDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HomeController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) {
        // 1. 유저 체크
        UserDao userDao = new UserDao();
        req.setAttribute("users", userDao.findAll());

        //2. question 갯수 체크
        QuestionDao questionDao = new QuestionDao();
        req.setAttribute("questions", questionDao.findAll());
        return "home.jsp";
    }
}
