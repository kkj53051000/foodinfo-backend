package com.kp.foodinfo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringToDateUtil {

    public static Date stringToDateProcess(String dateStr) {
        SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        Date date = null;
        try {
            date = fm.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return date;
    }

    public static String dateToStringProcess(Date date) {
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        String dateStr = transFormat.format(date);

        return dateStr;

    }
}
