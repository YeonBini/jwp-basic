package next.controller.qna;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.mvc.Controller;
import next.dao.AnswerDao;
import next.model.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class DeleteAnswerController implements Controller {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        AnswerDao answerDao = new AnswerDao();
        boolean isDeleted = answerDao.delete(Long.parseLong(req.getParameter("answerId")));

        ObjectMapper objectMapper = new ObjectMapper();
        resp.setContentType("application/json;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        if(isDeleted) {
            out.print(objectMapper.writeValueAsString(Result.ok()));
        } else {
            out.print(objectMapper.writeValueAsString(Result.fail("failed deleting answer")));
        }
        return null;
    }
}
