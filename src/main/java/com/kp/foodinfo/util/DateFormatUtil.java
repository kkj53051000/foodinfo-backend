package com.kp.foodinfo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateFormatUtil {

    public static Date stringToDateProcess(String dateStr) {
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = fm.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

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

    public static int stringToIntegerProcess(String dateStr) {
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");

        Date date = null;
        try {
            date = fm.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        int dateInt = dateToIntegerProcess(date);

        return dateInt;
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

    public static String dateToStringProcess(Date date) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");

        String dateStr = transFormat.format(date);

        return dateStr;
    }

    public static int dateToIntegerProcess(Date date) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd");

        String dateStr = transFormat.format(date);

        int dateInt = Integer.parseInt(dateStr.replace("-", ""));

        return dateInt;
    }

    public static String dateToStringDayTimeProcess(Date date) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String dateStr = transFormat.format(date);

        return dateStr;
    }

    public static Date dateToDateProcess(Date date) {
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");

        String dateStr = fm.format(date);

        try {
            date = fm.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date dateToDateTimeProcess(Date date) {
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String dateStr = fm.format(date);

        try {
            date = fm.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static Date dateToDateCustomTimeProcess(Date date, int hour, int minute, int second) {
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd " + hour + ":" + minute + ":" + second);

        String dateStr = fm.format(date);

        try {
            date = fm.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }


    public static Date utcToAsiaSeoulDate(Date date) {

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.HOUR, 9);

        calendar.setTime(date);

        Date asiaSeoulDate = calendar.getTime();

        return asiaSeoulDate;
    }

}
