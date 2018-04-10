package ru.sbt.jschool.session5.problem2.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TrainedDog extends Dog {
    private Map<String, String> tricks = new HashMap<>();

    public TrainedDog(String name, String breed, Double weight, Integer growth, Date dateOfBirth) {
        super(name, breed, weight, growth, dateOfBirth);
    }

    public Map<String, String> getTricks() {
        return tricks;
    }

    public void setTricks(Map<String, String> tricks) {
        this.tricks = tricks;
    }

    @Override
    public String toString() {
        return "TrainedDog{" +
                "tricks=" + tricks +
                '}';
    }
}
