package ru.sbt.jschool.session5.problem2.types;

import ru.sbt.jschool.session5.problem2.JSONFormatter;
import ru.sbt.jschool.session5.problem2.JSONTypeFormatter;

import java.util.Map;

public class NumberFormatter implements JSONTypeFormatter<Number> {
    @Override
    public String format(Number number, JSONFormatter formatter, Map<String, Object> ctx) throws IllegalAccessException {
        if (number instanceof Float || number instanceof Double) {
            return number.toString().replace("0.", ".");
        } else {
            return number.toString();
        }
    }
}
