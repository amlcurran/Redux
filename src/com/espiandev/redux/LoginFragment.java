package com.espiandev.redux;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LoginFragment extends TitledFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_login, container, false);
    }

    @Override
    public int getTitle() {
        return R.string.log_in;
    }

    @Override
    protected int getSubtitle() {
        return 0;
    }
}
