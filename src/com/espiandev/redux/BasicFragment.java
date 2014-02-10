package com.espiandev.redux;

import android.app.Activity;
import android.app.Fragment;

import com.espiandev.redux.animation.AnimationFactory;
import com.espiandev.redux.animation.AnimationFactoryProvider;
import com.espiandev.redux.network.NetworkHelper;
import com.espiandev.redux.network.NetworkHelperProvider;

public abstract class BasicFragment extends Fragment implements ResourceStringProvider, TitledItem {
    protected NetworkHelper networkHelper;
    protected AnimationFactory animationFactory;
    protected TitleHost titleHost = TitleHost.NONE;
    protected boolean hasSetTitle;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof NetworkHelperProvider) {
            networkHelper = ((NetworkHelperProvider) activity).getNetworkHelper();
        }
        if (activity instanceof AnimationFactoryProvider) {
            animationFactory = ((AnimationFactoryProvider) activity).getAnimationFactory();
        }
        if (activity instanceof TitleHost) {
            titleHost = (TitleHost) activity;
        }

        hasSetTitle = true;
        setTitles();
    }

    protected void setTitles() {
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
            setTitles();
        }

        hasSetTitle = false;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        titleHost = TitleHost.NONE;
    }
}
