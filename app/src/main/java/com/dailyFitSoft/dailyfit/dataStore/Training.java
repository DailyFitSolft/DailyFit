package com.dailyFitSoft.dailyfit.dataStore;

import java.util.Date;

public class Training {
    private int ID;
    private int IDExercise;
    private Date startDateTime;
    private Date stopDateTime;

    public Training(int id, int IDExercise, Date startDateTime, Date stopDateTime) {
        this.ID = id;
        this.IDExercise = IDExercise;
        this.startDateTime = startDateTime;
        this.stopDateTime = stopDateTime;
    }


    public int getID() {
        return ID;
    }

    public Date getStartDateTime() {
        return startDateTime;
    }

    public Date getStopDateTime() {
        return stopDateTime;
    }

    public int getIDExercise() {
        return IDExercise;
    }
}
