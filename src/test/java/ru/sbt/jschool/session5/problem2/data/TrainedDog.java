package ru.sbt.jschool.session5.problem2.data;

import java.util.*;

public class TrainedDog extends Dog {
    private Map<String, String> tricks = new LinkedHashMap<>();

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TrainedDog)) return false;
        if (!super.equals(o)) return false;
        TrainedDog that = (TrainedDog) o;
        return Objects.equals(getTricks(), that.getTricks());
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), getTricks());
    }
}
