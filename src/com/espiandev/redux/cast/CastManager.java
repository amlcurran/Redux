package com.espiandev.redux.cast;

import com.espiandev.redux.assets.Asset;

/**
 * Created by Alex Curran on 14/02/2014.
 */
public interface CastManager {

    void resumeScanning();

    void pauseScanning();

    void playAsset(Asset asset);

    boolean canCast();
}
