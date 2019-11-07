package core.nmvc;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Maps;

import com.google.common.collect.Sets;
import core.annotation.RequestMapping;
import core.annotation.RequestMethod;
import org.reflections.ReflectionUtils;

public class AnnotationHandlerMapping implements HandlerMapping{
    private Object[] basePackage;

    private Map<HandlerKey, HandlerExecution> handlerExecutions = Maps.newHashMap();

    public AnnotationHandlerMapping(Object... basePackage) {
        this.basePackage = basePackage;
    }

    public void initialize() {
        ControllerScanner scanner = new ControllerScanner(basePackage);
        Map<Class<?>, Object> controllers = scanner.getControllers();
        Set<Method> methods = getAllMethods(controllers);

        for (Method method : methods) {
            RequestMapping rm = method.getAnnotation(RequestMapping.class);
            handlerExecutions.put(
                        createHandlerKey(rm),
                        new HandlerExecution(controllers.get(method.getDeclaringClass()), method)
                    );

        }

    }

    private HandlerKey createHandlerKey(RequestMapping rm) {
        return new HandlerKey(rm.value(), rm.method());
    }

    private Set<Method> getAllMethods(Map<Class<?>, Object> controllers) {
        Set<Method> methods = Sets.newHashSet();
        for (Class clazz : controllers.keySet()) {
            methods.addAll(ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(RequestMapping.class)));
        }
        return methods;
    }

    public HandlerExecution getHandler(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        RequestMethod rm = RequestMethod.valueOf(request.getMethod().toUpperCase());
        return handlerExecutions.get(new HandlerKey(requestUri, rm));
    }
}
