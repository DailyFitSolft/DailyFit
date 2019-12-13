package com.dailyFitSoft.dailyfit.dataStore;

public class Profile {

    private String name;
    private double height;
    private double weight;
    private int age;

    public Profile(String name, double height, double weight, int age) {
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.age = age;
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

    public int getAge() {
        return age;
    }
}
