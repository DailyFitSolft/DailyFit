package com.dailyFitSoft.dailyfit;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class DateFormatter {

//    String oldPattern = "dd-MM-yyyy HH:mm:ss";

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    public static Date dateFromString(int day, int month, int year, int hour, int minute) throws IllegalArgumentException {
        try {
            String dayStr = day < 10 ? "0" + day : String.valueOf(day);
            String monthStr = month < 10 ? "0" + month : String.valueOf(month);
            String hourStr = hour < 10 ? "0" + hour : String.valueOf(hour);
            String minuteStr = minute < 10 ? "0" + minute : String.valueOf(minute);
            //String dateInString = dayStr + "-" + monthStr + "-" + year + " " + hourStr + ":" + minuteStr;
            String dateInString = dayStr + "-" + monthStr + "-" + year;
            Date date = formatter.parse(dateInString);
            return date;
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }


    public static Date dateFromString(String dateStr) throws IllegalArgumentException{
        try {
            return formatter.parse(dateStr);
        }
        catch(Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException();
        }
    }

    public static String stringFromDate(Date date){
        return formatter.format(date);
    }

    public static Date getCurrentDate(){
        Date now = new Date();
        return dateFromString(formatter.format(now));
    }

    public static SimpleDateFormat getDateFormat(){
        return formatter;
    }
}
