package next.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ForwardController implements Controller {
    private String forwardUrl;

    public ForwardController(String forwardUrl) {
        this.forwardUrl = forwardUrl;
        if(this.forwardUrl == null) {
            throw new NullPointerException("Forward Url is null : 이동할 url을 선택하세요");
        }
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        return this.forwardUrl;
    }
}
