package ru.sbt.jschool.session5.problem2.types;
import org.apache.commons.lang3.StringUtils;
import ru.sbt.jschool.session5.problem2.JSONFormatter;
import ru.sbt.jschool.session5.problem2.JSONFormatterImpl;
import ru.sbt.jschool.session5.problem2.JSONTypeFormatter;

import java.util.Map;

public class MapFormatter implements JSONTypeFormatter<Map> {
    @Override
    public String format(Map aMap, JSONFormatter formatter, Map<String, Object> ctx) throws IllegalAccessException {
        Map<Object, Object> map = aMap;
        if (map.size() == 0){
            return "[]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[')
                .append('\n');
        for(Map.Entry<Object, Object> entry : map.entrySet()){
            stringBuilder.append(StringUtils.repeat("    ", 1))
                    .append(formatter.marshall(entry.getKey()))
                    .append(" : ")
                    .append(formatter.marshall(entry.getValue())).append(",\n");
        }
        return stringBuilder.append(StringUtils.repeat("    ", 1)).append(']').toString();
    }
}
