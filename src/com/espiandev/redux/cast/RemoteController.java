package com.espiandev.redux.cast;

import com.espiandev.redux.assets.Asset;
import com.espiandev.redux.network.ReduxUrlHelper;

public interface RemoteController {
    void load(Asset asset, ReduxUrlHelper urlHelper);

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
