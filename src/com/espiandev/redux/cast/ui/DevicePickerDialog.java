package com.espiandev.redux.cast.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.espiandev.redux.R;

public class DevicePickerDialog extends DialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.Theme_Redux_Dialog);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
