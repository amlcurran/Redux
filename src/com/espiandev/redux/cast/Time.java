package com.espiandev.redux.cast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Time {

    private long millis;

    public Time(long millis) {

        this.millis = millis;
    }

    public static Time fromMillis(long millis) {
        return new Time(millis);
    }

    public long getMillis() {
        return millis;
    }

    public String formatHms() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date date = new Date(millis);
        return dateFormat.format(date);
    }
}
