package ru.sbt.jschool.session5.problem2.types;

import ru.sbt.jschool.session5.problem2.JSONFormatter;
import ru.sbt.jschool.session5.problem2.JSONTypeFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author NIzhikov
 */
public class DateFormatter implements JSONTypeFormatter<Date> {
    @Override public String format(Date date, JSONFormatter formatter, Map<String, Object> ctx) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }
}