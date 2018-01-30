package com.rest.test;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.servlets.CrossOriginFilter;

public class App {
    public static void main(String[] args) throws Exception {
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");

        Server jettyServer = new Server(8080);
        jettyServer.setHandler(context);

        ServletHolder jerseyServlet = context.addServlet(
             org.glassfish.jersey.servlet.ServletContainer.class, "/*");
        jerseyServlet.setInitOrder(0);

        FilterHolder filter = new FilterHolder();
        filter.setInitParameter("allowedOrigins", "http://localhost:8080,http://localhost:8089");
        filter.setInitParameter("allowedMethods", "POST,GET,OPTIONS,PUT,DELETE,HEAD");
        filter.setInitParameter("allowedHeaders", "X-PINGOTHER, Origin, X-Requested-With, Content-Type, Accept");
        filter.setInitParameter("preflightMaxAge", "728000");
        filter.setInitParameter("allowCredentials", "true");
        CrossOriginFilter corsFilter = new CrossOriginFilter();
        filter.setFilter(corsFilter);

        // Tells the Jersey Servlet which REST service/class to load.
        //jerseyServlet.setInitParameter("jersey.config.server.provider.classnames", EntryPoint.class.getCanonicalName());
        jerseyServlet.setInitParameter("jersey.config.server.provider.classnames",
                "com.rest.test.MediaResource;com.rest.test.VoteResource");
        try {
            jettyServer.start();
            jettyServer.join();
        } finally {
            //logger.errror("error during server starting",e)
            jettyServer.stop();
            jettyServer.destroy();
        }
    }
}


