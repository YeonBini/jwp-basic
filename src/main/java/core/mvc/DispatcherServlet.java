package core.mvc;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.common.collect.Lists;
import core.nmvc.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@WebServlet(name = "dispatcher", urlPatterns = "/", loadOnStartup = 1)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);

    private LegacyHandlerMapping lhm;
    private AnnotationHandlerMapping ahm;

    private List<HandlerMapping> handlerMappings = Lists.newArrayList();
    private List<HandlerAdapter> handlerAdapters = Lists.newArrayList();

    @Override
    public void init() throws ServletException {
        lhm = new LegacyHandlerMapping();
        ahm = new AnnotationHandlerMapping();

        lhm.initMapping();
        ahm.initialize();

        handlerMappings.add(lhm);
        handlerMappings.add(ahm);

        handlerAdapters.add(new ControllerHandlerAdapter());
        handlerAdapters.add(new AnnotationHandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestUri = req.getRequestURI();
        logger.debug("Method : {}, Request URI : {}", req.getMethod(), requestUri);

        Object handler = getHandler(req);

        if(handler == null) {
            throw new IllegalArgumentException("존재하지 않는 url 입니다.");
        }
        try {
            ModelAndView mav = execute(handler, req, resp);
            View view = mav.getView();
            view.render(mav.getModel(), req, resp);
        } catch (Throwable e) {
            logger.error("Exception : {}", e);
            throw new ServletException(e.getMessage());
        }
    }

    private ModelAndView execute(Object handler, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        for(HandlerAdapter handlerAdapter : handlerAdapters) {
            if(handlerAdapter.support(handler)) {
                return handlerAdapter.handle(req, resp, handler);
            }
        }
        return null;
    }

    private Object getHandler(HttpServletRequest req) {
        Object handler;
        for(HandlerMapping hd :handlerMappings) {
            handler = hd.getHandler(req);
            if(handler != null) return handler;
        }
        return null;
    }
}
