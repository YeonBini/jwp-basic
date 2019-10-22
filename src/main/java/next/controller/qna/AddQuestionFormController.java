package next.controller.qna;

import core.mvc.AbstractController;
import core.mvc.ModelAndView;
import next.controller.UserSessionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddQuestionFormController extends AbstractController {
    @Override
    public ModelAndView execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if(!UserSessionUtils.isLogined(req.getSession())) {
            req.getSession().setAttribute("notLogined", true);
            return jspView("redirect:/");
        }

        return jspView("/qna/form.jsp").addObject("name", req.getParameter("name"));
    }
}
