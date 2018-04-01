package ru.sbt.jschool.session5.problem1;

import java.lang.reflect.Field;

/**
 */
public class SQLGenerator {
    public <T> String insert(Class<T> clazz) {
        return null;
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
}
