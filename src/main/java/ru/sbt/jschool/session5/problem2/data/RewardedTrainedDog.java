package ru.sbt.jschool.session5.problem2.data;

import java.util.Date;

public class RewardedTrainedDog extends TrainedDog {
    private int rank;
    private String nameOfRank;
    public RewardedTrainedDog(String name, String breed, Double weight, Integer growth, Date dateOfBirth, int rank, String nameOfRank) {
        super(name, breed, weight, growth, dateOfBirth);
        this.rank = rank;
        this.nameOfRank = nameOfRank;
    }
}
