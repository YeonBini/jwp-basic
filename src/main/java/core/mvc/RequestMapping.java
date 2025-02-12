package core.mvc;

import java.util.HashMap;
import java.util.Map;

import next.controller.qna.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import next.controller.HomeController;
import next.controller.user.CreateUserController;
import next.controller.user.ListUserController;
import next.controller.user.LoginController;
import next.controller.user.LogoutController;
import next.controller.user.ProfileController;
import next.controller.user.UpdateFormUserController;
import next.controller.user.UpdateUserController;

public class RequestMapping {
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
    private Map<String, Controller> mappings = new HashMap<>();

    void initMapping() {
        mappings.put("/", new HomeController());
        mappings.put("/users/form", new ForwardController("/user/form.jsp"));
        mappings.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        mappings.put("/users", new ListUserController());
        mappings.put("/users/login", new LoginController());
        mappings.put("/users/profile", new ProfileController());
        mappings.put("/users/logout", new LogoutController());
        mappings.put("/users/create", new CreateUserController());
        mappings.put("/users/updateForm", new UpdateFormUserController());
        mappings.put("/users/update", new UpdateUserController());
        mappings.put("/qna/show", new ShowController());
        mappings.put("/qna/form", new AddQuestionFormController());
        mappings.put("/qna/update", new UpdateQuestionForm());
        mappings.put("/qna/delete", new DeleteQuestionController());
        mappings.put("/api/qna/addAnswer", new AddAnswerController());
        mappings.put("/api/qna/deleteAnswer", new DeleteAnswerController());
        mappings.put("/api/qna/list", new ApiQuestionListController());
        mappings.put("/api/qna/addQuestion", new AddQuestionController());
        mappings.put("/api/qna/update", new ApiQuestionUpdateController());
        mappings.put("/api/qna/delete", new ApiQuestionDeleteController());

        logger.info("Initialized Request Mapping!");
    }

    public Controller findController(String url) {
        return mappings.get(url);
    }

    void put(String url, Controller controller) {
        mappings.put(url, controller);
    }
}
