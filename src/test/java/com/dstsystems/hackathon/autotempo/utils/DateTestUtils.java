package com.dstsystems.hackathon.autotempo.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTestUtils {

    public static Date buildDate(String date) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return sdf.parse(date);
    }

    public static Date buildDateTime(String dateTime) throws ParseException {
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).parse(dateTime);
        } catch (ParseException e) {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US).parse(dateTime);
        }
    }

}
