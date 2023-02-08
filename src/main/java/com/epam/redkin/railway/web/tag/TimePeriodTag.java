package com.epam.redkin.railway.web.tag;


import jakarta.servlet.jsp.tagext.SimpleTagSupport;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

public class TimePeriodTag extends SimpleTagSupport {

    private String dateFrom;
    private String dateTo;
    private String locale;

    @Override
    public void doTag() throws IOException {
        Duration between;
        if (locale.equals("en")) {
            between = Duration.between(LocalDateTime.parse(dateFrom), LocalDateTime.parse(dateTo));
            getJspContext().getOut().print(builder(between, "%s day ", "%s h :%02d min", "%02d min"));
        }
        if (locale.equals("ua")) {
            between = Duration.between(LocalDateTime.parse(dateFrom), LocalDateTime.parse(dateTo));
            getJspContext().getOut().print(builder(between, "%s день ", "%s год :%02d хв", "%02d хв"));
        }
    }

    private String builder(Duration between, String dayFormat, String hourMinuteFormat, String minuteFormat) {
        StringBuilder builder = new StringBuilder();
        long days = between.toDays();
        if (days > 0) {
            builder.append(String.format(dayFormat, days));
        }
        long hours = between.toHours();
        if (hours != 0) {
            builder.append(String.format(hourMinuteFormat, between.toHours() % 24, between.toMinutes() % 60));
        } else {
            builder.append(String.format(minuteFormat, between.toMinutes() % 60));
        }
        return builder.toString();
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
