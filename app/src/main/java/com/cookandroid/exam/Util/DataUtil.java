package com.cookandroid.exam.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DataUtil {
    public final static String YEAR_FORMAT = "yyyy";
    public final static String MONTH_FORMAT = "MM";
    public final static String DAY_FORMAT = "d";
    public final static String HEADER_FORMAT = "yyyy MM";

    public static String getDate(long date, String pattern) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(pattern, Locale.ENGLISH);
            Date d = new Date(date);
            return formatter.format(d).toUpperCase();
        } catch (Exception e) {
            return " ";
        }
    }
}
