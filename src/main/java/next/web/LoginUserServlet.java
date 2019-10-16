package next.web;

import core.db.DataBase;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/login")
public class LoginUserServlet extends HttpServlet {

    final private Logger log = LoggerFactory.getLogger(LoginUserServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = DataBase.findUserById(req.getParameter("userId"));

        if(null == user || !user.getPassword().equals(req.getParameter("password"))) {
            RequestDispatcher rd = req.getRequestDispatcher("/user/login_failed.html");
            rd.forward(req, resp);
        }

        log.debug("login user : {}", user);
        HttpSession session = req.getSession();
        session.setAttribute("user", user);

        log.debug("session : {}" , session);
        resp.sendRedirect("/");
    }
}
