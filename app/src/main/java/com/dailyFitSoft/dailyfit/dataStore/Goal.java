package com.dailyFitSoft.dailyfit.dataStore;

import java.util.Date;

public class Goal {

    private int ID;
    private String name;
    private Date endDate;
    private boolean isAchived;

    public Goal(int ID, String name, Date endDate, boolean isAchived) {
        this.ID = ID;
        this.name = name;
        this.endDate = endDate;
        this.isAchived = isAchived;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public Date getEndDate() {
        return endDate;
    }

    public boolean isAchived() {
        return isAchived;
    }
}
