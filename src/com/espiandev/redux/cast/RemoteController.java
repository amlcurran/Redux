package com.espiandev.redux.cast;

import com.google.android.gms.cast.MediaInfo;

public interface RemoteController {
    void load(MediaInfo mediaInfo);
    void play();
    void pause();
    void seekTo(long millis);
    void setListener(Listener listener);

    interface Listener {
        Listener NONE = new Listener() {
            @Override
            public void onPaused() {

            }

            @Override
            public void onResumed() {

            }

            @Override
            public void onBuffering() {

            }
        };

        void onPaused();
        void onResumed();
        void onBuffering();
    }

}
