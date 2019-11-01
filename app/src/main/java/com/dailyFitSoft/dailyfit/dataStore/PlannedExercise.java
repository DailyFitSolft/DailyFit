package com.dailyFitSoft.dailyfit.dataStore;

import java.util.Date;

public class PlannedExercise {

    private int ID;
    private int exerciseID;
    private int trainTime;
    private int repeatCount;
    private Date plannedDateAndTime;

    public PlannedExercise(int ID, int exerciseID, int trainTime, int repeatCount, Date plannedDateAndTime) {
        this.ID = ID;
        this.exerciseID = exerciseID;
        this.trainTime = trainTime;
        this.repeatCount = repeatCount;
        this.plannedDateAndTime = plannedDateAndTime;
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

    public Date getPlannedDateAndTime() {
        return plannedDateAndTime;
    }
}
