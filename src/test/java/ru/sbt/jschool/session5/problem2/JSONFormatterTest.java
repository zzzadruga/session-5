package ru.sbt.jschool.session5.problem2;

import org.junit.Test;
import ru.sbt.jschool.session5.problem2.data.Dog;
import ru.sbt.jschool.session5.problem2.data.RewardedTrainedDog;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class JSONFormatterTest  {
    @Test public void addNewJSONTypeFormatterTest() throws IllegalAccessException {
        JSONFormatterImpl jsonFormatter = new JSONFormatterImpl();
        assertFalse(jsonFormatter.addType(LocalDateTime.class, (localDateTime, formatter, deep) -> localDateTime.toLocalDate().format(DateTimeFormatter.ofPattern("dd:MM:yyyy"))));
        assertEquals("10:04:2018", jsonFormatter.marshall(LocalDateTime.of(2018, 4, 10, 16, 0)));
    }
    @Test public void replaceJSONTypeFormatterTest() throws IllegalAccessException {
        JSONFormatterImpl jsonFormatter = new JSONFormatterImpl();
        Date now = new Date(2018-1900, 3, 10);
        assertEquals("10.04.2018", jsonFormatter.marshall(now));
        assertTrue(jsonFormatter.addType(Date.class, (date, formatter, deep) -> {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            return dateFormat.format(date);
        }));
        assertEquals("10/04/2018", jsonFormatter.marshall(now));
    }
    @Test public void rewardedTrainedDogTest() throws IllegalAccessException, FileNotFoundException {
        RewardedTrainedDog rewardedTrainedDog = new RewardedTrainedDog("Жучка", "Дворняга", 10.56, 70, new Date(2008 - 1900, 4, 12), 10, "Class");
        rewardedTrainedDog.getChildren().add(new Dog("Филя", "Дворняга", 0.21, 40, new Date(2017 - 1900, 3, 7)));
        rewardedTrainedDog.getChildren().add(new Dog("Бобик", "Дворняга", 9.1, 65, new Date(2005 - 1900, 11, 12)));
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
        File output = new File("src/test/resources/RewardedTrainedDogOutput.txt");
        StringBuilder sb = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/test/resources/RewardedTrainedDogOutput.txt")))){
            String line;
            while ((line = reader.readLine()) != null){
                sb.append(sb.length() != 0 ? '\n' : "").append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(sb.toString(), new JSONFormatterImpl().marshall(rewardedTrainedDog));
    }
}
