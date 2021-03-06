package com.espiandev.redux.navigation;

import android.support.v4.app.Fragment;

/**
 * Created by Alex Curran on 10/02/2014.
 */
public interface Stacker {

    int addFragment(Fragment fragment);

    void pushFragment(Fragment fragment);

    void removeFragment();
}
