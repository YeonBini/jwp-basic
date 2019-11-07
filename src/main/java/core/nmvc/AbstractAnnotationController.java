package core.nmvc;

import core.mvc.Controller;
import core.mvc.JsonView;
import core.mvc.JspView;
import core.mvc.ModelAndView;

public abstract class AbstractAnnotationController{
    protected ModelAndView jspView(String forwardUrl) {
        return new ModelAndView(new JspView(forwardUrl));
    }

    protected ModelAndView jsonView() {
        return new ModelAndView(new JsonView());
    }
}
