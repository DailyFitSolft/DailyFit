package com.dailyFitSoft.dailyfit.dataStore;

import java.util.Date;

public class Training {
    private int ID;
    private String activityName;
    private String startTime;
    private String stopTime;
    private Date startDate;

    public Training(int id, String activityName, String startTime, String stopTime, Date startDate) {
        this.ID = id;
        this.activityName = activityName;
        this.startTime = startTime;
        this.stopTime = stopTime;
        this.startDate = startDate;
    }


    public int getID() {
        return ID;
    }

    public String getActivityName() {
        return activityName;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getStopTime() {
        return stopTime;
    }

    public Date getStartDate() {
        return startDate;
    }

}
