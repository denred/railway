package com.epam.redkin.railway.web.listener;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContextListener.class);
    public ContextListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        LOGGER.info("Servlet context initialization starts");

        ServletContext servletContext = sce.getServletContext();
        initI18N(servletContext);

        LOGGER.info("Servlet context initialization finished");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        /* This method is called when the servlet Context is undeploy or Application Server shuts down. */
    }


    /**
     * Initializes i18n subsystem.
     */
    private void initI18N(ServletContext servletContext) {
        LOGGER.debug("I18N subsystem initialization started");

        String localesValue = servletContext.getInitParameter("locales");
        if (StringUtils.isBlank(localesValue)) {
            LOGGER.warn("'locales' init parameter is empty, the default encoding will be used");
        } else {
            List<String> locales = new ArrayList<>();
            StringTokenizer st = new StringTokenizer(localesValue);
            while (st.hasMoreTokens()) {
                String localeName = st.nextToken();
                locales.add(localeName);
            }

            LOGGER.debug("Application attribute set: locales --> " + locales);
            servletContext.setAttribute("locales", locales);
        }

        LOGGER.debug("I18N subsystem initialization finished");
    }
}
