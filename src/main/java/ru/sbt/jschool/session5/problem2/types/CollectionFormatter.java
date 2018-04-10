package ru.sbt.jschool.session5.problem2.types;

import org.apache.commons.lang3.StringUtils;
import ru.sbt.jschool.session5.problem2.JSONFormatter;
import ru.sbt.jschool.session5.problem2.JSONTypeFormatter;

import java.util.Collection;
import java.util.Map;

public class CollectionFormatter implements JSONTypeFormatter<Collection> {
    @Override
    public String format(Collection collection, JSONFormatter formatter, Map<String, Object> ctx) throws IllegalAccessException {
            if (collection.size() == 0){
                return "[]";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('[')
                    .append('\n');
            for (Object element : collection){
                stringBuilder.append(StringUtils.repeat("    ", 1))
                        .append(formatter.marshall(element))
                        .append(',')
                        .append('\n');
            }
            stringBuilder.append(StringUtils.repeat("    ", 1))
                    .append(']');
            return stringBuilder.toString();
    }
}
