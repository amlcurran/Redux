package com.espiandev.redux;

import android.app.Activity;
import android.app.Fragment;

public abstract class TitledFragment extends Fragment implements TitledItem {

    protected TitleHost titleHost = TitleHost.NONE;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof TitleHost) {
            titleHost = (TitleHost) activity;
        }

        titleHost.setTitle(activity.getString(getTitle()));

        if (getSubtitle() != 0) {
            titleHost.setSubtitle(activity.getString(getSubtitle()));
        } else {
            titleHost.setSubtitle("");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        titleHost = TitleHost.NONE;
    }

}
