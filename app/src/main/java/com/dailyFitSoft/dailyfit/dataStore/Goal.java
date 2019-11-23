package com.dailyFitSoft.dailyfit.dataStore;

import java.util.Date;

public class Goal {

    private int ID;
    private GoalType goalType;
    private Date endDate;
    private boolean isAchived;
    private int valueToAchive;
    private int achivedValue;

    public Goal(int ID, GoalType goalType, Date endDate, boolean isAchived, int achivedValue, int valueToAchive) {
        this.ID = ID;
        this.goalType = goalType;
        this.endDate = endDate;
        this.isAchived = isAchived;
        this.achivedValue = achivedValue;
        this.valueToAchive = valueToAchive;
    }

    public int getID() {
        return ID;
    }

    public GoalType getGoalType() {
        return goalType;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isAchived() {
        return isAchived;
    }

    public int getValueToAchive() {
        return valueToAchive;
    }

    public int getAchivedValue() {
        return achivedValue;
    }

    public void setAchivedValue(int achivedValue) {
        this.achivedValue = achivedValue;
    }
}
