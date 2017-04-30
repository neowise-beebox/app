package com.project.spaceapps.beebox.beebox.model;

/**
 * Created by Matheus on 30/04/2017.
 */

public class RankingPlace {

    String name;
    double score;

    public RankingPlace() {

    }

    public RankingPlace(String name, double score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
