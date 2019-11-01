package com.dailyFitSoft.dailyfit.dataStore;

public class Exercise {

    private int ID;
    private String name;
    private int difficulty;
    private int burnedCalories;

    public Exercise(int ID, String name, int difficulty, int burnedCalories) {
        this.ID = ID;
        this.name = name;
        this.difficulty = difficulty;
        this.burnedCalories = burnedCalories;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getBurnedCalories() {
        return burnedCalories;
    }
}
