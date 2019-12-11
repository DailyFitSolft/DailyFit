package com.dailyFitSoft.dailyfit.dataStore;

import java.util.Date;

public class Weight {

    private int ID;
    private Date date;
    private float weight;
    public static boolean alreadyAsked = false;

    public Weight(int ID, Date date, float weight) {
        this.ID = ID;
        this.date = date;
        this.weight = weight;
    }

    public int getID() {
        return ID;
    }

    public Date getDate() {
        return date;
    }

    public float getWeight() {
        return weight;
    }
}
