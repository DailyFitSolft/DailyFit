package com.dailyFitSoft.dailyfit;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatter {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static Date dateFromString(int day, int month, int year, int hour, int minute) throws IllegalArgumentException {
        try
        {
            String dayStr = day < 10 ? "0" + day : String.valueOf(day);
            String monthStr = month < 10 ? "0" + month : String.valueOf(month);
            String hourStr = hour < 10 ? "0" + hour : String.valueOf(hour);
            String minuteStr = minute < 10 ? "0" + minute : String.valueOf(minute);
            String dateInString = dayStr + "-" + monthStr + "-" + year + " " + hourStr + ":" + minuteStr + ":00";
            Date date = formatter.parse(dateInString);
            return date;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public static String stringFromDate(Date date){
        return formatter.format(date);
    }
}
