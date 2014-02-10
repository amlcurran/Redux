package com.espiandev.redux.navigation;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

import com.espiandev.redux.R;

public class FragmentManagerStacker implements Stacker {

    private Activity activity;

    public FragmentManagerStacker(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int addFragment(Fragment fragment) {
        return activity.getFragmentManager().beginTransaction().replace(R.id.host_frame, fragment)
                .commit();
    }

    @Override
    public void pushFragment(Fragment fragment) {
        activity.getFragmentManager().beginTransaction().replace(R.id.host_frame, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(String.valueOf(fragment.getClass()))
                .commit();
    }

    @Override
    public void removeFragment() {
        Fragment currentFragment = activity.getFragmentManager().findFragmentById(R.id.host_frame);
        activity.getFragmentManager().beginTransaction().remove(currentFragment)
                .commit();
    }
}