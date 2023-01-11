package com.epam.redkin.web.controller.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class ContextListener implements ServletContextListener {
    private static final Logger log = LoggerFactory.getLogger(ContextListener.class);



    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //log("Servlet context initialization starts");

        ServletContext servletContext = sce.getServletContext();
        //initLog4J(servletContext);
        initCommandFactory();
        initI18N(servletContext);

        //log("Servlet context initialization finished");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //log("Servlet context destruction starts");
        // do nothing
       // log("Servlet context destruction finished");
    }

    /**
     * Initializes i18n subsystem.
     */
    private void initI18N(ServletContext servletContext) {
        log.debug("I18N subsystem initialization started");

        String localesValue = servletContext.getInitParameter("locales");
        if (localesValue == null || localesValue.isEmpty()) {
            log.warn("'locales' init parameter is empty, the default encoding will be used");
        } else {
            List<String> locales = new ArrayList<>();
            StringTokenizer st = new StringTokenizer(localesValue);
            while (st.hasMoreTokens()) {
                String localeName = st.nextToken();
                locales.add(localeName);
            }

            log.debug("Application attribute set: locales --> " + locales);
            servletContext.setAttribute("locales", locales);
        }

        log.debug("I18N subsystem initialization finished");
    }

    /**
     * Initializes CommandFactory.
     */
    private void initCommandFactory() {
        log.debug("ICommand container initialization started");

        // initialize commands container
        // just load class to JVM
        try {
            Class.forName("com.epam.redkin.web.controller.command.CommandFactory");
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        log.debug("ICommand container initialization finished");
    }


    /**
     * Initializes log4j framework.
     *
     * @param servletContext ServletContext.
     */
    /*private void initLog4J(ServletContext servletContext) {
        //log("Log4J initialization started");
        try {
            PropertyConfigurator.configure(servletContext.getRealPath("_log4j.properties"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //log("Log4J initialization finished");
    }*/
}
