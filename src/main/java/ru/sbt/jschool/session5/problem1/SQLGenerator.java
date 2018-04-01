package ru.sbt.jschool.session5.problem1;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 */
public class SQLGenerator {
    public <T> String insert(Class<T> clazz) {
        StringBuilder query = new StringBuilder();
        StringBuilder values = new StringBuilder();
        query.append("INSERT INTO ")
             .append(getTableName(clazz));
        getFields(clazz).forEach((k, v) -> {
            if (!v.equals(FieldsAnnotation.NONE)){
                query.append(values.length() == 0 ? "(" : ", ")
                     .append(k);
                values.append(values.length() == 0 ? "?" : ", ?");
            }
        });
        query.append(") VALUES (")
             .append(values)
             .append(")");
        return query.toString();
    }

    public <T> String update(Class<T> clazz) {
        StringBuilder query = new StringBuilder();
        Map fields = getFields(clazz);
        fields.forEach((k, v) -> {
            if (v.equals(FieldsAnnotation.COLUMN_NAME)){
                query.append(query.length() == 0 ? "UPDATE " + getTableName(clazz) + " SET " : ", ")
                        .append(k)
                        .append(" = ?");
            }
        });
        query.append(" WHERE ");
        fields.forEach((k, v) -> {
            if (v.equals(FieldsAnnotation.PRIMARY_KEY)){
                query.append(k)
                     .append(" = ? AND ");
            }
        });
        return query.delete(query.length() - " AND ".length(), query.length()).toString();
    }

    public <T> String delete(Class<T> clazz) {
        return null;
    }

    public <T> String select(Class<T> clazz) {
        StringBuilder query = new StringBuilder();
        Map fields = getFields(clazz);
        fields.forEach((k, v) -> {
            if (v.equals(FieldsAnnotation.COLUMN_NAME)){
                query.append(query.length() == 0 ? "SELECT " : ", ")
                     .append(k);
            }
        });
        query.append(" FROM ")
             .append(getTableName(clazz));
        fields.forEach((k, v) -> {
            if (v.equals(FieldsAnnotation.PRIMARY_KEY)){
                query.append(query.substring(query.length() - 1).equals("?") ? " AND " : " WHERE ")
                        .append(k)
                        .append(" = ?");
            }
        });
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
            else{
                fields.put(field.getName(), FieldsAnnotation.NONE);
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
