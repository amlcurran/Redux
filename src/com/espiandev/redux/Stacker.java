package com.espiandev.redux;

import android.app.Fragment;

/**
 * Created by Alex Curran on 10/02/2014.
 */
public interface Stacker {

    int addFragment(Fragment fragment);

    void pushFragment(Fragment fragment);

    void removeFragment();
}
