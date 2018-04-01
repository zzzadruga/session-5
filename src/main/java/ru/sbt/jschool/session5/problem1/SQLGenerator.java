package ru.sbt.jschool.session5.problem1;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 */
public class SQLGenerator {
    private static final String SEPARATOR_COMMA = ", ";
    private static final String SEPARATOR_AND = " AND ";
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
        System.out.println(getFields(clazz));
        return query.toString();
    }

    public <T> String update(Class<T> clazz) {
        StringBuilder query = new StringBuilder();
        Map fields = getFields(clazz);
        query.append("UPDATE ")
             .append(getTableName(clazz))
             .append(" SET ")
             .append(getFormattedColumnList(fields, FieldsAnnotation.COLUMN_NAME, true, SEPARATOR_COMMA))
             .append(" WHERE ")
             .append(getFormattedColumnList(fields, FieldsAnnotation.PRIMARY_KEY, true, SEPARATOR_AND));
        return query.toString();
    }

    public <T> String delete(Class<T> clazz) {
        StringBuilder query = new StringBuilder();
        query.append("DELETE FROM ")
             .append(getTableName(clazz))
             .append(" WHERE ")
             .append(getFormattedColumnList(getFields(clazz), FieldsAnnotation.PRIMARY_KEY, true, SEPARATOR_AND));
        return query.toString();
    }

    public <T> String select(Class<T> clazz) {
        StringBuilder query = new StringBuilder();
        Map fields = getFields(clazz);
        query.append("SELECT ")
             .append(getFormattedColumnList(fields, FieldsAnnotation.COLUMN_NAME, false, SEPARATOR_COMMA))
             .append(" FROM ")
             .append(getTableName(clazz))
             .append(" WHERE ")
             .append(getFormattedColumnList(fields, FieldsAnnotation.PRIMARY_KEY, true, SEPARATOR_AND));
        return query.toString();
    }
    private String getFormattedColumnList(Map<String, FieldsAnnotation> fields, FieldsAnnotation annotation, boolean isArguments, String separator){
        StringBuilder listOfColumns = new StringBuilder();
        String argument = isArguments ? " = ?" : "";
        fields.forEach((k, v) -> {
            if (v.equals(annotation)) {
                listOfColumns.append(listOfColumns.length() == 0 ? "" : argument + separator);
                listOfColumns.append(k);
            }
        });
        listOfColumns.append(argument);
        return listOfColumns.toString();
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
