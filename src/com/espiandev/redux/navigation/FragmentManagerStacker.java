package com.espiandev.redux.navigation;

import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.Fragment;

import com.espiandev.redux.R;

public class FragmentManagerStacker implements Stacker {

    private FragmentActivity activity;

    public FragmentManagerStacker(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public int addFragment(Fragment fragment) {
        return activity.getSupportFragmentManager().beginTransaction().replace(R.id.host_frame, fragment)
                .commit();
    }

    @Override
    public void pushFragment(Fragment fragment) {
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.host_frame, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(String.valueOf(fragment.getClass()))
                .commit();
    }

    @Override
    public void removeFragment() {
        Fragment currentFragment = activity.getSupportFragmentManager().findFragmentById(R.id.host_frame);
        activity.getSupportFragmentManager().beginTransaction().remove(currentFragment)
                .commit();
    }
}