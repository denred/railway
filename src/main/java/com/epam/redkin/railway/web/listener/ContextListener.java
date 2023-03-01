package com.epam.redkin.railway.web.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

@WebListener
public class ContextListener implements ServletContextListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContextListener.class);

    public ContextListener() {
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
        LOGGER.info("Servlet context initialization starts");

        ServletContext servletContext = event.getServletContext();
        initI18n(servletContext);

        LOGGER.info("Servlet context initialization finished");
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        // This method is called when the servlet Context is undeploy or Application Server shuts down.
    }

    private void initI18n(ServletContext servletContext) {
        LOGGER.debug("I18n subsystem initialization started");

        String localesValue = servletContext.getInitParameter("locales");
        List<String> locales = new ArrayList<>();
        if (StringUtils.isNotBlank(localesValue)) {
            StringTokenizer tokenizer = new StringTokenizer(localesValue);
            while (tokenizer.hasMoreTokens()) {
                String localeName = tokenizer.nextToken();
                locales.add(localeName);
            }
        } else {
            LOGGER.warn("'locales' init parameter is empty, the default locale 'en' will be used");
            locales.add("en");
        }

        LOGGER.debug("Application attribute set: locales --> " + locales);
        servletContext.setAttribute("locales", locales);

        String defaultLocale = Locale.getDefault().getLanguage();
        if (locales.contains(defaultLocale)) {
            servletContext.setAttribute("locale", defaultLocale);
        } else {
            servletContext.setAttribute("locale", "en");
            defaultLocale = "en";
        }
        LOGGER.info("Default locale: {}", defaultLocale);

        LOGGER.debug("I18n subsystem initialization finished");
    }

}
