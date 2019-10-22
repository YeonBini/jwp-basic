package core.mvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelAndView {

    private View view;
    private Map<String, Object> model = new HashMap<>();

    public ModelAndView(View view) {
        this.view = view;
    }

    public ModelAndView addObject(String attributeName, Object attributeValue) {
        this.model.put(attributeName, attributeValue);
        return this;
    }

    public Map<String, Object> getModel() {
        // unmodifiableMap을 통해 read-only 속성을 부여한다.
        return Collections.unmodifiableMap(model);
    }

    public View getView() {
        return this.view;
    }
}
