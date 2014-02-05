package com.espiandev.redux.animation;

import android.view.View;

public interface AnimationFactory {

    public void fadeIn(View view);
    public void refadeIn(View view, Runnable runnable);
    public void fadeOut(View view);

}
