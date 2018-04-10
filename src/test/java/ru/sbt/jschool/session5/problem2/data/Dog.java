package ru.sbt.jschool.session5.problem2.data;

import java.util.*;

public class Dog {
    private String name;
    private String breed;
    private Double weight;
    private Integer growth;
    private Date dateOfBirth;
    private List<Dog> children = new ArrayList<>();
    private Map<Calendar, List<String>> dailyFood = new LinkedHashMap<>();

    public Dog(String name, String breed, Double weight, Integer growth, Date dateOfBirth) {
        this.name = name;
        this.breed = breed;
        this.weight = weight;
        this.growth = growth;
        this.dateOfBirth = dateOfBirth;
    }

    public List<Dog> getChildren() {
        return children;
    }

    public void setChildren(List<Dog> children) {
        this.children = children;
    }

    public Map<Calendar, List<String>> getDailyFood() {
        return dailyFood;
    }

    public void setDailyFood(Map<Calendar, List<String>> dailyFood) {
        this.dailyFood = dailyFood;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dog)) return false;
        Dog dog = (Dog) o;
        return Objects.equals(name, dog.name) &&
                Objects.equals(breed, dog.breed) &&
                Objects.equals(weight, dog.weight) &&
                Objects.equals(growth, dog.growth) &&
                Objects.equals(dateOfBirth, dog.dateOfBirth) &&
                Objects.equals(getChildren(), dog.getChildren()) &&
                Objects.equals(getDailyFood(), dog.getDailyFood());
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, breed, weight, growth, dateOfBirth, getChildren(), getDailyFood());
    }
}
