package com.espiandev.redux.animation;

import android.view.View;
import android.widget.TextView;

public interface AnimationFactory {

    public void fadeIn(View view);
    public void refadeIn(View view, Runnable runnable);
    public void fadeAndChangeText(TextView textView, CharSequence text);
    public void fadeOut(View view);
    public void upAndOut(View view);
    public void upAndIn(View view);
    public void downAndOut(View view);
    public void downAndIn(View view);
    public void cancelAnimations(View... views);

}
