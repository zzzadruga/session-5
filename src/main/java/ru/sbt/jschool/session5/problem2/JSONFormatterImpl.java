package ru.sbt.jschool.session5.problem2;

import org.apache.commons.lang3.StringUtils;
import ru.sbt.jschool.session5.problem2.data.Dog;
import ru.sbt.jschool.session5.problem2.data.RewardedTrainedDog;
import ru.sbt.jschool.session5.problem2.types.*;

import java.util.*;

/**
 * @author NIzhikov
 */
public class JSONFormatterImpl implements JSONFormatter {
    private Map<Class, JSONTypeFormatter> types = new HashMap<>();

    public JSONFormatterImpl() {
        types.put(Date.class, new DateFormatter());
        types.put(Calendar.class, new CalendarFormatter());
        types.put(Collection.class, new CollectionFormatter());
        types.put(Map.class, new MapFormatter());
        types.put(Number.class, new NumberFormatter());
        types.put(String.class, new StringFormatter());
        types.put(Object.class, new ObjectFormatter());
    }

    @Override public String marshall(Object obj) throws IllegalAccessException {
        if (obj == null)
            return "";
        Map<String, Object> ctx = new HashMap<>();
        if (types.containsKey(obj.getClass())){
            return types.get(obj.getClass()).format(obj, this, ctx);
        }
        if (obj instanceof Date) {
            return types.get(Date.class).format(obj, this, ctx);
        }
        if (obj instanceof Number) {
            return types.get(Number.class).format(obj, this, ctx);
        }
        if (obj instanceof Calendar) {
            return types.get(Calendar.class).format(obj, this, ctx);
        }
        if (obj instanceof Collection || obj.getClass().isArray()) {
            return types.get(Collection.class).format(obj.getClass().isArray() ? Arrays.asList(obj) : obj, this, ctx);
        }
        if (obj instanceof Map) {
            return types.get(Map.class).format(obj, this, ctx);
        }
        return types.get(Object.class).format(obj, this, ctx);
    }

    @Override public <T> boolean addType(Class<T> clazz, JSONTypeFormatter<T> format) {
        return types.put(clazz, format) != null;
    }

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
        System.out.println(new JSONFormatterImpl().marshall(rewardedTrainedDog));
    }
}
