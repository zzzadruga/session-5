package ru.sbt.jschool.session5.problem2;

import java.util.*;

public class Dog {
    private String name;
    private String breed;
    private Double weight;
    private Integer growth;
    private Date dateOfBirth;
    private List<Dog> children = new ArrayList<>();
    private Map<Calendar, List<String>> dailyFood = new HashMap<>();

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
    public String toString() {
        return "Dog{" +
                "name='" + name + '\'' +
                ", breed='" + breed + '\'' +
                ", weight=" + weight +
                ", growth=" + growth +
                ", dateOfBirth=" + dateOfBirth +
                ", children=" + children +
                ", dailyFood=" + dailyFood +
                '}';
    }
}
