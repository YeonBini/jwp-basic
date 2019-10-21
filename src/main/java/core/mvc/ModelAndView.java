package core.mvc;

import java.util.Map;

public interface ModelAndView {
    Map<String, Object> modelAttributes();

    String view();

}
