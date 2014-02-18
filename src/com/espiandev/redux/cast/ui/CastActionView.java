package com.espiandev.redux.cast.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;

import com.espiandev.redux.R;
import com.espiandev.redux.view.ActionView;

public class CastActionView extends ActionView {

    public CastActionView(Context context) {
        this(context, null, 0);
    }

    public CastActionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CastActionView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        LayoutInflater.from(context).inflate(R.layout.view_action_cast, this, true);

    }
}
