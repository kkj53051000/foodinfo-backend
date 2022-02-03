package com.kp.foodinfo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatTestUtil {

    public static Date stringToDateDayProcess(String dateStr) {
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = fm.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date stringToDateDayTimeProcess(String dateStr) {
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = null;
        try {
            date = fm.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String dateToStringDayProcess(Date date) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");

        String dateStr = transFormat.format(date);

        return dateStr;
    }

    public static String dateToStringDayTimeProcess(Date date) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String dateStr = transFormat.format(date);

        return dateStr;
    }

    public static Date dateToDateDayProcess(Date date) {
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");

        String dateStr = fm.format(date);

        try {
            date = fm.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }
}
