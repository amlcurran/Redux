package com.espiandev.redux;

import android.app.Activity;

import com.espiandev.redux.ResourceStringProvider;
import com.espiandev.redux.TitledFragment;
import com.espiandev.redux.animation.AnimationFactory;
import com.espiandev.redux.animation.AnimationFactoryProvider;
import com.espiandev.redux.network.NetworkHelper;
import com.espiandev.redux.network.NetworkHelperProvider;

public abstract class BasicFragment extends TitledFragment implements ResourceStringProvider {
    protected NetworkHelper networkHelper;
    protected AnimationFactory animationFactory;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof NetworkHelperProvider) {
            networkHelper = ((NetworkHelperProvider) activity).getNetworkHelper();
        }
        if (activity instanceof AnimationFactoryProvider) {
            animationFactory = ((AnimationFactoryProvider) activity).getAnimationFactory();
        }
    }
}
