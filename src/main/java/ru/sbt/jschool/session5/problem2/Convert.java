package ru.sbt.jschool.session5.problem2;

import org.apache.commons.lang3.StringUtils;
import ru.sbt.jschool.session5.problem2.data.Dog;
import ru.sbt.jschool.session5.problem2.data.RewardedTrainedDog;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

public class Convert {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    private static final String INDENT = "    ";

    public static void main(String[] args) throws IllegalAccessException {
        RewardedTrainedDog rewardedTrainedDog = new RewardedTrainedDog("Жучка", "Дворняга", 10.56, 70, new Date(2008, 4, 12), 10, "Class");
        rewardedTrainedDog.getChildren().add(new Dog("Филя", "Дворняга", 0.21, 40, new Date(2017, 3, 7)));
        rewardedTrainedDog.getChildren().add(new Dog("Бобик", "Дворняга", 9.1, 65, new Date(2005, 11, 12)));
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(2018, Calendar.APRIL, 5);
        Calendar now = Calendar.getInstance();
        now.set(2018, Calendar.APRIL, 6);
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.set(2018, Calendar.APRIL, 7);
        rewardedTrainedDog.getDailyFood().put(yesterday, Arrays.asList("Молоко", "Каша", "Творог"));
        rewardedTrainedDog.getDailyFood().put(now, Arrays.asList("Гречка", "Мясо", "Рыба"));
        rewardedTrainedDog.getDailyFood().put(tomorrow, Collections.singletonList("Ничего"));
        rewardedTrainedDog.getTricks().put("360", "Повернуться на 360 градусов");
        rewardedTrainedDog.getTricks().put("Задние лапы", "Встать на задние лапы");
        System.out.println(Convert.toJSON(rewardedTrainedDog));
    }

    public static String toJSON(Object object) throws IllegalAccessException {
        return toJSON(object, 0);
    }
    private static String toJSON(Object object, int deep) throws IllegalAccessException {
        if (object == null){
            return "null";
        }
        StringBuilder stringBuilder = new StringBuilder();
        Field[] fields = object.getClass().getDeclaredFields();
        stringBuilder.append('\n').append(StringUtils.repeat(INDENT, deep)).append('{');
        for(Field field : getInheritedPrivateFields(object.getClass())){
            field.setAccessible(true);
            stringBuilder.append('\n')
                         .append(StringUtils.repeat(INDENT, deep + 1))
                         .append('"')
                         .append(field.getName())
                         .append('"')
                         .append(':')
                         .append(' ')
                         .append(getValue(field.get(object), deep + 1))
                         .append(',')
                         .append('\n');
        }
        stringBuilder.append(StringUtils.repeat(INDENT, deep)).append('}');
        return stringBuilder.toString();
    }

    private static List<Field> getInheritedPrivateFields(Class<?> clazz) {
        List<Field> result = new ArrayList<>();
        Class<?> type = clazz;
        int deep = 0;
        while (type != null && type != Object.class) {
            for (int i = type.getDeclaredFields().length - 1; i >= 0; i--) {
                result.add(type.getDeclaredFields()[i]);
            }
            type = type.getSuperclass();
        }
        Collections.reverse(result);
        return result;
    }

    private static String getValue(Object object, int deep) throws IllegalAccessException {
        if (object == null) {
            return "";
        }
        if (object instanceof Date) {
            return dateFormat.format((Date)object);
        }
        if (object instanceof Number) {
            if (object instanceof Float || object instanceof Double) {
                return object.toString().replace("0.", ".");
            } else {
                return object.toString();
            }
        }
        if (object instanceof Calendar) {
            return dateFormat.format(((Calendar) object).getTime());
        }
        if (object instanceof String) {
            return "\"" + object.toString() + "\"";
        }

        if (object instanceof Collection || object.getClass().isArray()) {
            Collection collection = object.getClass().isArray() ? Arrays.asList(object) : (Collection) object;
            if (collection.size() == 0){
                return "[]";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('[')
                         .append('\n');
            for (Object element : collection){
                stringBuilder.append(StringUtils.repeat(INDENT, deep + 1))
                             .append(getValue(element, deep))
                             .append(',')
                             .append('\n');
            }
            stringBuilder.append(StringUtils.repeat(INDENT, deep + 1))
                         .append(']');
            return stringBuilder.toString();
        }
        if (object instanceof Map) {
            Map<Object, Object> map = (Map)object;
            if (map.size() == 0){
                return "[]";
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append('[')
                         .append('\n');
            for(Map.Entry<Object, Object> entry : map.entrySet()){
                stringBuilder.append(StringUtils.repeat(INDENT, deep + 1))
                             .append(getValue(entry.getKey(), deep))
                             .append(" : ")
                             .append(getValue(entry.getValue(), deep + 1)).append(',').append('\n');
            }
            return stringBuilder.append(StringUtils.repeat(INDENT, deep + 1)).append(']').toString();
        }
        return toJSON(object, deep);
    }
}
