package ru.otus.server;

import java.util.Arrays;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import ru.otus.dao.ClientDao;
import ru.otus.service.TemplateProcessor;
import ru.otus.service.UserAuthService;
import ru.otus.servlet.AuthorizationFilter;
import ru.otus.servlet.LoginServlet;

public class WebServerWithFilterBasedSecurity extends WebServerSimple {

    private final UserAuthService authService;

    public WebServerWithFilterBasedSecurity(int port,
        UserAuthService authService,
        TemplateProcessor templateProcessor,
        ClientDao clientDao) {
        super(port, templateProcessor, clientDao);
        this.authService = authService;
    }

    @Override
    protected Handler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler
            .addServlet(new ServletHolder(new LoginServlet(templateProcessor, authService)),
                "/login");
        AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths).forEachOrdered(path -> servletContextHandler
            .addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }
}
