package com.dailyFitSoft.dailyfit.dataStore;

import com.dailyFitSoft.dailyfit.DateFormatter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Profile {

    private String name;
    private double height;
    private double weight;
    private Date birthday;

    public Profile(String name, double height, double weight, Date birthday) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.birthday = birthday;
    }

    public Profile(String name, double height, double weight, String birthday){
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.birthday = DateFormatter.dateFromString(birthday);
    }

    public String getName() {
        return name;
    }

    public double getHeight() {
        return height;
    }

    public double getWeight() {
        return weight;
    }

    public Date getBirthday() {
        return birthday;
    }
}
