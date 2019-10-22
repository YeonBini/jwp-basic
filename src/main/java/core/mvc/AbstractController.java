package core.mvc;

import next.view.JsonView;
import next.view.JspView;

public abstract class AbstractController implements Controller {

    public ModelAndView jspView(String forwardUrl) {
        return new ModelAndView(new JspView(forwardUrl));
    }

    public ModelAndView jsonView() {
        return new ModelAndView(new JsonView());
    }
}
