package com.espiandev.redux.cast;

import com.google.android.gms.cast.MediaInfo;

public interface RemoteController {
    void load(MediaInfo mediaInfo);
    void play();
    void pause();
    void seekTo(long millis);
}
