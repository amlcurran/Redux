package com.espiandev.redux.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import com.espiandev.redux.R;

public abstract class ActionView extends FrameLayout {

    public ActionView(Context context) {
        this(context, null, 0);
    }

    public ActionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ActionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setBackgroundDrawable(context.getResources().getDrawable(R.drawable.action_view_background));
    }



}
