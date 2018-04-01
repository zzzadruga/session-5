package ru.sbt.jschool.session5.problem1;

import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 */
public class SQLGenerator {
    public <T> String insert(Class<T> clazz) {
        StringBuilder query = new StringBuilder();
        Set fields;
        query.append("INSERT INTO ")
             .append(getTableName(clazz))
             .append((fields = getFields(clazz).keySet()).toString().replace("[", "(").replace("]", ")"))
             .append(" VALUES (?")
             .append((fields.size() > 1) ? StringUtils.repeat(", ?", fields.size() - 1) : "")
             .append(")");
        System.out.println(getFields(clazz));

        return query.toString();
    }

    public <T> String update(Class<T> clazz) {
        return null;
    }

    public <T> String delete(Class<T> clazz) {
        return null;
    }

    public <T> String select(Class<T> clazz) {
        StringBuilder query = new StringBuilder();
        StringBuilder primaryKey = new StringBuilder();
        for(Field field : clazz.getDeclaredFields()){
            if (field.getAnnotation(Column.class) != null){
                query.append(query.length() == 0 ? "SELECT " : ", ")
                     .append((field.getAnnotation(Column.class).name().isEmpty() ? field.getName() : field.getAnnotation(Column.class).name()).toLowerCase());
            }
            else if (field.getAnnotation(PrimaryKey.class) != null) {
                    primaryKey.append(primaryKey.length() == 0 ? "" : " AND ")
                              .append(field.getName().toLowerCase()).append(" = ?");
            }
        }
        query.append(" FROM ")
             .append(clazz.getAnnotation(Table.class) != null ? clazz.getAnnotation(Table.class).name() : clazz.getSimpleName())
             .append(" WHERE ")
             .append(primaryKey);
        return query.toString();
    }

    private <T> Map<String, FieldsAnnotation> getFields(Class<T> clazz){
        Map<String, FieldsAnnotation> fields = new LinkedHashMap<>();
        for(Field field : clazz.getDeclaredFields()){
            if (field.getAnnotation(Column.class) != null){
                fields.put(getColumnName(Column.class, field), FieldsAnnotation.COLUMN_NAME);
            }
            else if (field.getAnnotation(PrimaryKey.class) != null) {
                fields.put(getColumnName(PrimaryKey.class, field), FieldsAnnotation.PRIMARY_KEY);
            }
        }
        return fields;
    }

    private <T extends Annotation> String getColumnName(Class<T> clazz, Field field){
        String methodValue = field.getName().toLowerCase();
        try {
            methodValue = String.valueOf(clazz.getMethod("name").invoke(field.getAnnotation(clazz)));
            return (methodValue.isEmpty() ? field.getName() : methodValue).toLowerCase();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return methodValue;
    }

    private <T> String getTableName(Class<T> clazz){
        return clazz.getAnnotation(Table.class) != null ? clazz.getAnnotation(Table.class).name() : clazz.getSimpleName();
    }

}
