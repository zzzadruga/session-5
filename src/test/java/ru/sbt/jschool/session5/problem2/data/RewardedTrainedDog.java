package ru.sbt.jschool.session5.problem2.data;

import java.util.Date;
import java.util.Objects;

public class RewardedTrainedDog extends TrainedDog {
    private int rank;
    private String nameOfRank;
    public RewardedTrainedDog(String name, String breed, Double weight, Integer growth, Date dateOfBirth, int rank, String nameOfRank) {
        super(name, breed, weight, growth, dateOfBirth);
        this.rank = rank;
        this.nameOfRank = nameOfRank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RewardedTrainedDog)) return false;
        if (!super.equals(o)) return false;
        RewardedTrainedDog that = (RewardedTrainedDog) o;
        return rank == that.rank &&
                Objects.equals(nameOfRank, that.nameOfRank);
    }

    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), rank, nameOfRank);
    }
}
