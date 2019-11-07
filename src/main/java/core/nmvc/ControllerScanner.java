package core.nmvc;

import com.google.common.collect.Maps;
import core.annotation.Controller;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public class ControllerScanner {
    private Logger log = LoggerFactory.getLogger(ControllerScanner.class);

    private Reflections reflections;
    private Map<Class<?>, Object> controllers = Maps.newHashMap();

    public ControllerScanner(Object... basePackage) {
        this.reflections = new Reflections(basePackage);
    }

    public Map<Class<?>, Object> getControllers()  {
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Controller.class);

        return initiateControllers(typesAnnotatedWith);
    }

    private Map<Class<?>, Object> initiateControllers(Set<Class<?>> typesAnnotatedWith)  {
        try {
            for (Class clazz : typesAnnotatedWith) {
                controllers.put(clazz, clazz.getDeclaredConstructor().newInstance());
            }
        } catch (Exception e) {
            log.error("Errors on initiate controllers");
        }
        return controllers;
    }

}
