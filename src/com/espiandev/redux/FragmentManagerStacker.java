package com.espiandev.redux;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;

public class FragmentManagerStacker implements Stacker {

    private Activity activity;

    public FragmentManagerStacker(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int addFragment(Fragment fragment) {
        return activity.getFragmentManager().beginTransaction().add(R.id.host_frame, fragment)
                .commit();
    }

    @Override
    public void pushFragment(Fragment fragment) {
        activity.getFragmentManager().beginTransaction().replace(R.id.host_frame, fragment)
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .addToBackStack(String.valueOf(fragment.getClass()))
                .commit();
    }
}