package ru.sbt.jschool.session5.problem2.types;

import ru.sbt.jschool.session5.problem2.JSONFormatter;
import ru.sbt.jschool.session5.problem2.JSONTypeFormatter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarFormatter implements JSONTypeFormatter<Calendar> {
    @Override public String format(Calendar calendar, JSONFormatter formatter, int deep) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format((calendar).getTime());
    }
}
