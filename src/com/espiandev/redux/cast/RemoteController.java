package com.espiandev.redux.cast;

import com.google.android.gms.cast.MediaInfo;

public interface RemoteController {
    void load(MediaInfo mediaInfo);

    void play();

    void pause();

    void seekTo(Time millis);

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

            @Override
            public void onElapsedTimeChanged(Time elapsedTime) {

            }

            @Override
            public void onTotalTimeChanged(Time totalTime) {

            }
        };

        void onPaused();

        void onResumed();

        void onBuffering();

        void onElapsedTimeChanged(Time elapsedTime);

        void onTotalTimeChanged(Time totalTime);
    }

}
