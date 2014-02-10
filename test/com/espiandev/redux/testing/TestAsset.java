package com.espiandev.redux.testing;

import com.espiandev.redux.Channel;
import com.espiandev.redux.assets.Asset;

import java.util.Date;

public class TestAsset extends Asset {

    public static final long DURATION = 1000;
    public static final String KEY = "keyyy";
    public static final String UUID = "uuidBeMe";
    public static final String DESCRIPTION = "iAmBeingDescribed";
    public static final String NAME = "disBeMyName";

    @Override
    public long getDuration() {
        return DURATION;
    }

    @Override
    public Date getBroadcastDate() {
        return new Date(0);
    }

    @Override
    public Channel getChannel() {
        return new Channel();
    }

    @Override
    public String getKey() {
        return KEY;
    }

    @Override
    public String getUuid() {
        return UUID;
    }

    @Override
    public String getDescription() {
        return DESCRIPTION;
    }

    @Override
    public String getName() {
        return NAME;
    }
}
