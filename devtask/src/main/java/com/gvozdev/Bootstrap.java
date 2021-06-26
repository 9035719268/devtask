package com.gvozdev;

import com.gvozdev.config.ApplicationConfig;
import com.gvozdev.http.XmlParserServlet;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.HttpConnectionFactory;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletHandler;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Bootstrap {
    public static void main(String[] args) throws Exception {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        ApplicationConfig applicationConfig = context.getBean("applicationConfig", ApplicationConfig.class);

        Server server = new Server(applicationConfig.getPort());

        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(true);
        resourceHandler.setWelcomeFiles(new String[]{"index.html"});

        resourceHandler.setResourceBase("src/main/resources/static/index.html");

        ServletHandler servletHandler = new ServletHandler();
        servletHandler.addServletWithMapping(XmlParserServlet.class, "/parse-xml");

        HandlerList handlerList = new HandlerList();
        handlerList.setHandlers(new Handler[]{resourceHandler, servletHandler, new DefaultHandler()});
        server.setHandler(handlerList);

        ServerConnector serverConnector = new ServerConnector(server, 1, 1, new HttpConnectionFactory());
        serverConnector.setHost(applicationConfig.getDestAddr());
        serverConnector.setPort(applicationConfig.getDestPort());

        server.addConnector(serverConnector);

        server.start();
        server.join();
    }
}
