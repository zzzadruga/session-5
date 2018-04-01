package ru.sbt.jschool.session5.problem1;

import org.springframework.util.StringUtils;

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
        Set<String> columnSet = getColumnSet(getFields(clazz), FieldsAnnotation.COLUMN_NAME, FieldsAnnotation.PRIMARY_KEY);
        return query
                .append("INSERT INTO ")
                .append(getTableName(clazz))
                .append("(")
                .append(getFormattedColumnList(columnSet, false, SEPARATOR_COMMA))
                .append(") VALUES (")
                .append(String.join(SEPARATOR_COMMA, Collections.nCopies(columnSet.size(), "?")))
                .append(")")
                .toString();
    }

    public <T> String update(Class<T> clazz) {
        StringBuilder query = new StringBuilder();
        Map<String, FieldsAnnotation> fields = getFields(clazz);
        return query.append("UPDATE ")
                .append(getTableName(clazz))
                .append(" SET ")
                .append(getFormattedColumnList(fields, true, SEPARATOR_COMMA, FieldsAnnotation.COLUMN_NAME))
                .append(" WHERE ")
                .append(getFormattedColumnList(fields, true, SEPARATOR_AND, FieldsAnnotation.PRIMARY_KEY))
                .toString();
    }

    public <T> String delete(Class<T> clazz) {
        StringBuilder query = new StringBuilder();
        return query.append("DELETE FROM ")
                .append(getTableName(clazz))
                .append(" WHERE ")
                .append(getFormattedColumnList(getFields(clazz), true, SEPARATOR_AND, FieldsAnnotation.PRIMARY_KEY))
                .toString();
    }

    public <T> String select(Class<T> clazz) {
        StringBuilder query = new StringBuilder();
        Map<String, FieldsAnnotation> fields = getFields(clazz);
        return query.append("SELECT ")
                .append(getFormattedColumnList(fields, false, SEPARATOR_COMMA, FieldsAnnotation.COLUMN_NAME))
                .append(" FROM ")
                .append(getTableName(clazz))
                .append(" WHERE ")
                .append(getFormattedColumnList(fields, true, SEPARATOR_AND, FieldsAnnotation.PRIMARY_KEY))
                .toString();
    }

    private String getFormattedColumnList(Map<String, FieldsAnnotation> fields, boolean isArguments, String separator, FieldsAnnotation... annotations) {
        return StringUtils.collectionToDelimitedString(getColumnSet(fields, annotations), separator, "", isArguments ? " = ?" : "");
    }

    private String getFormattedColumnList(Set<String> fields, boolean isArguments, String separator) {
        return StringUtils.collectionToDelimitedString(fields, separator, "", isArguments ? " = ?" : "");
    }

    private Set<String> getColumnSet(Map<String, FieldsAnnotation> fields, FieldsAnnotation... annotations) {
        Set<FieldsAnnotation> annotationSet = new LinkedHashSet<>(Arrays.asList(annotations));
        Set<String> columnSet = new LinkedHashSet<>();
        fields.forEach((k, v) -> {
            if (annotationSet.contains(v)) {
                columnSet.add(k);
            }
        });
        return columnSet;
    }

    private <T> Map<String, FieldsAnnotation> getFields(Class<T> clazz) {
        Map<String, FieldsAnnotation> fields = new LinkedHashMap<>();
        for (Field field : clazz.getDeclaredFields()) {
            if (field.getAnnotation(Column.class) != null) {
                fields.put(getColumnName(Column.class, field), FieldsAnnotation.COLUMN_NAME);
            } else if (field.getAnnotation(PrimaryKey.class) != null) {
                fields.put(getColumnName(PrimaryKey.class, field), FieldsAnnotation.PRIMARY_KEY);
            } else {
                fields.put(field.getName(), FieldsAnnotation.NONE);
            }
        }
        return fields;
    }

    private <T extends Annotation> String getColumnName(Class<T> clazz, Field field) {
        String methodValue = field.getName().toLowerCase();
        try {
            methodValue = String.valueOf(clazz.getMethod("name").invoke(field.getAnnotation(clazz)));
            return (methodValue.isEmpty() ? field.getName() : methodValue).toLowerCase();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return methodValue;
    }

    private <T> String getTableName(Class<T> clazz) {
        return clazz.getAnnotation(Table.class) != null ? clazz.getAnnotation(Table.class).name() : clazz.getSimpleName();
    }
}
