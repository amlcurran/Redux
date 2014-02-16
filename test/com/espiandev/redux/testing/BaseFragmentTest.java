package com.espiandev.redux.testing;

import android.app.Fragment;

import com.espiandev.redux.animation.AnimationFactory;
import com.espiandev.redux.assets.AssetListParser;
import com.espiandev.redux.auth.TokenStorage;
import com.espiandev.redux.cast.CastManager;
import com.espiandev.redux.downloads.Downloader;
import com.espiandev.redux.network.NetworkHelper;

import org.junit.Before;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.util.ActivityController;

public abstract class BaseFragmentTest<T extends Fragment> {
    protected FragmentTestingActivity activity;
    protected T fragment;
    @Mock
    protected TokenStorage mockTokenStorage;
    @Mock
    protected NetworkHelper mockNetworkHelper;
    @Mock
    protected AnimationFactory mockAnimationFactory;
    @Mock
    protected AssetListParser mockListParser;
    @Mock
    protected Downloader mockDownloader;
    @Mock
    protected CastManager mockCastManager;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        ActivityController<FragmentTestingActivity> controller = Robolectric.buildActivity(FragmentTestingActivity.class);
        activity = controller.get();
        createFragment();
        attachMocks();
        activity.setFragment(fragment);
        controller.create().start().resume();

    }

    protected abstract void createFragment();

    private void attachMocks() {
        activity.animationFactory = mockAnimationFactory;
        activity.tokenStorage = mockTokenStorage;
        activity.networkHelper = mockNetworkHelper;
        activity.listParser = mockListParser;
        activity.downloader = mockDownloader;
        activity.castManager = mockCastManager;
    }
}
