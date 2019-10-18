import next.controller.*;

import java.util.HashMap;
import java.util.Map;

public class RequestMapping {
    private static Map<String, Controller> controllerMap = new HashMap<>();

    static {
        controllerMap.put("/", new HomeController());
        controllerMap.put("/users", new ListUserController());
        controllerMap.put("/users/form", new ForwardController("/user/form.jsp"));
        controllerMap.put("/users/loginForm", new ForwardController("/user/login.jsp"));
        controllerMap.put("/users/login", new LoginController());
        controllerMap.put("/users/logout", new LogoutController());
        controllerMap.put("/users/profile", new ProfileController());
        controllerMap.put("/users/updateForm", new UpdateUserFormController());
        controllerMap.put("/users/update", new UpdateUserController());
        controllerMap.put("/users/create", new CreateUserController());
    }

    public Controller getController(String path) {
        return controllerMap.get(path);
    }
}
