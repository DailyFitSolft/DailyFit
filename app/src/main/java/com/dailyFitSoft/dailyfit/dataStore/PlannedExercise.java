package com.dailyFitSoft.dailyfit.dataStore;

import androidx.annotation.NonNull;

import java.util.Date;

public class PlannedExercise {

    private int ID;
    private int exerciseID;
    private int trainTime;
    private int repeatCount;
    private String plannedDate;
    private String plannedTime;

    public PlannedExercise(int ID, int exerciseID, int trainTime, int repeatCount, String plannedDate, String plannedTime) {
        this.ID = ID;
        this.exerciseID = exerciseID;
        this.trainTime = trainTime;
        this.repeatCount = repeatCount;
        this.plannedDate = plannedDate;
        this.plannedTime= plannedTime;
    }

    public int getID() {
        return ID;
    }

    public int getExerciseID() {
        return exerciseID;
    }

    public int getTrainTime() {
        return trainTime;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public String getPlannedDate() {
        return plannedDate;
    }

    @NonNull
    @Override
    public String toString() {
        return " Czas: " + trainTime + " minut Powtórzenia: " + repeatCount + " Godzina: " + plannedTime ;
    }
}
