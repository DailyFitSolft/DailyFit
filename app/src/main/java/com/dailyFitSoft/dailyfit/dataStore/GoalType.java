package com.dailyFitSoft.dailyfit.dataStore;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public enum GoalType {

    CZAS_W_RUCHU(1),
    SPALONE_KALORIE(2);

    private int value;
    private static Map map = new HashMap();

    private GoalType(int value){
        this.value = value;
    }

    static{
        for (GoalType goalType : GoalType.values()) {
            map.put(goalType.value, goalType);
        }
    }

    public static GoalType valueOf(int value){
        return (GoalType)map.get(value);
    }

    public int getValue(){
        return value;
    }

    public String toString(){
        switch(value){
            case 1:
                return "Czas w ruchu";
            case 2:
                return "Spalone kalorie";
        }
        return null;
    }

    public static List<GoalType> getGoalTypeList(){
        List<GoalType> goalTypes = new LinkedList<>();
        goalTypes.add(CZAS_W_RUCHU);
        goalTypes.add(SPALONE_KALORIE);
        return goalTypes;
    }
}
