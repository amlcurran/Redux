package com.espiandev.redux.cast;

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
}
