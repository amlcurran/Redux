package com.espiandev.redux;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.espiandev.redux.animation.AnimationFactory;
import com.espiandev.redux.animation.AnimationFactoryProvider;
import com.espiandev.redux.auth.LoginListener;
import com.espiandev.redux.auth.TokenStorage;
import com.espiandev.redux.auth.TokenStorageProvider;
import com.espiandev.redux.network.NetworkHelper;
import com.espiandev.redux.network.NetworkHelperProvider;

import java.util.ArrayList;

public class FragmentTestingActivity extends Activity implements TitleHost, NetworkHelperProvider, AnimationFactoryProvider,
        TokenStorageProvider, LoginListener, SearchListener {

    private Fragment fragment;
    private CharSequence title;
    private CharSequence subtitle;
    public AnimationFactory animationFactory;
    public NetworkHelper networkHelper;
    public TokenStorage tokenStorage;
    public boolean onLoginCalled;
    public boolean onSearchResultCalled;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (fragment != null) {
            getFragmentManager().beginTransaction().add(R.id.host_frame, fragment)
                    .commit();
        }
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
    }

    @Override
    public void setSubtitle(CharSequence subtitle) {
        this.subtitle = subtitle;
    }

    public CharSequence getTitleHostSubtitle() {
        return subtitle;
    }

    public CharSequence getTitleHostTitle() {
        return title;
    }

    @Override
    public AnimationFactory getAnimationFactory() {
        return animationFactory;
    }

    @Override
    public NetworkHelper getNetworkHelper() {
        return networkHelper;
    }

    @Override
    public TokenStorage getTokenStorage() {
        return tokenStorage;
    }

    @Override
    public void onLogin() {
        onLoginCalled = true;
    }

    @Override
    public void onSearchResult(
            String query, ArrayList<Asset> results) {
        onSearchResultCalled = true;
    }
}
