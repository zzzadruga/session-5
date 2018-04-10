package ru.sbt.jschool.session5.problem2.types;

import ru.sbt.jschool.session5.problem2.JSONFormatter;
import ru.sbt.jschool.session5.problem2.JSONTypeFormatter;

public class StringFormatter implements JSONTypeFormatter<String> {
    @Override
    public String format(String s, JSONFormatter formatter, int deep) {
        return s;
    }
}
