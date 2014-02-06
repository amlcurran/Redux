package com.espiandev.redux;

import android.view.View;

import com.espiandev.redux.animation.AnimationFactory;

public class NonAnimationFactory implements AnimationFactory {
    @Override
    public void fadeIn(View view) {
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void refadeIn(View view, Runnable runnable) {
        fadeIn(view);
        runnable.run();
    }

    @Override
    public void fadeOut(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    @Override
    public void upAndOut(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    @Override
    public void upAndIn(View view) {
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void downAndOut(View view) {
        view.setVisibility(View.INVISIBLE);
    }

    @Override
    public void downAndIn(View view) {
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void cancelAnimations(View... views) {

    }
}
