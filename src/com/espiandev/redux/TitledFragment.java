package com.espiandev.redux;

import android.app.Activity;
import android.app.Fragment;

public abstract class TitledFragment extends Fragment implements TitledItem {

    protected TitleHost titleHost = TitleHost.NONE;
    private boolean hasSetTitle;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof TitleHost) {
            titleHost = (TitleHost) activity;
        }

        hasSetTitle = true;
        setTitles(activity);
    }

    private void setTitles(Activity activity) {
        titleHost.setTitle(getHostedTitle(titleHost));

        if (getHostedSubtitle(titleHost) != null) {
            titleHost.setSubtitle(getHostedSubtitle(titleHost));
        } else {
            titleHost.setSubtitle("");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!hasSetTitle) {
            setTitles(getActivity());
        }

        hasSetTitle = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        titleHost = TitleHost.NONE;
    }

}
