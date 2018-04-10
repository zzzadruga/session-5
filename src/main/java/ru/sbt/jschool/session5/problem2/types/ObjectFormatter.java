package ru.sbt.jschool.session5.problem2.types;

import org.apache.commons.lang3.StringUtils;
import ru.sbt.jschool.session5.problem2.JSONFormatter;
import ru.sbt.jschool.session5.problem2.JSONTypeFormatter;

import java.lang.reflect.Field;
import java.util.*;

public class ObjectFormatter implements JSONTypeFormatter<Object>{
    @Override
    public String format(Object object, JSONFormatter formatter, Map<String, Object> ctx) throws IllegalAccessException {
        StringBuilder stringBuilder = new StringBuilder();
        Field[] fields = object.getClass().getDeclaredFields();
        stringBuilder.append('\n').append(StringUtils.repeat("    ", 0)).append('{');
        for(Field field : getInheritedPrivateFields(object.getClass())){
            field.setAccessible(true);
            stringBuilder.append('\n')
                    .append(StringUtils.repeat("    ", 1))
                    .append('"')
                    .append(field.getName())
                    .append('"')
                    .append(':')
                    .append(' ')
                    .append(formatter.marshall(field.get(object)))
                    .append(',')
                    .append('\n');
        }
        stringBuilder.append(StringUtils.repeat("    ", 0)).append('}');
        return stringBuilder.toString();
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
