package ru.sbt.jschool.session5.problem2.types;

import org.apache.commons.lang3.StringUtils;
import ru.sbt.jschool.session5.problem2.JSONFormatter;
import ru.sbt.jschool.session5.problem2.JSONTypeFormatter;

import java.lang.reflect.Field;
import java.util.*;

public class ObjectFormatter implements JSONTypeFormatter<Object>{
    @Override public String format(Object object, JSONFormatter formatter, int deep) throws IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        Field[] fields = object.getClass().getDeclaredFields();
        sb.append('{');
        for(Field field : getInheritedPrivateFields(object.getClass())){
            field.setAccessible(true);
            sb.append(sb.length() != 1 ? ",\n" : "")
                    .append('\n')
                    .append(StringUtils.repeat(formatter.INDENT, deep + 1))
                    .append('"')
                    .append(field.getName())
                    .append('"')
                    .append(':')
                    .append(' ')
                    .append(formatter.marshall(field.get(object), deep + 1));
        }
        sb.append('\n')
                .append(StringUtils.repeat(formatter.INDENT, deep))
                .append('}');
        return sb.toString();
    }
    private static List<Field> getInheritedPrivateFields(Class<?> clazz) {
        List<Field> result = new ArrayList<>();
        Class<?> type = clazz;
        while (type != null && type != Object.class) {
            for (int i = type.getDeclaredFields().length - 1; i >= 0; i--) {
                result.add(type.getDeclaredFields()[i]);
            }
            type = type.getSuperclass();
        }
        Collections.reverse(result);
        return result;
    }
}
