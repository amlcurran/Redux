package com.espiandev.redux;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.espiandev.redux.animation.AnimationFactory;
import com.espiandev.redux.auth.TokenStorage;
import com.espiandev.redux.auth.TokenStorageProvider;
import com.espiandev.redux.network.VolleyHelper;
import com.espiandev.redux.network.VolleyHelperProvider;

public class FragmentTestingActivity extends Activity implements TitleHost, VolleyHelperProvider, AnimationFactoryProvider,
        TokenStorageProvider {

    private Fragment fragment;
    private CharSequence title;
    private CharSequence subtitle;
    public AnimationFactory animationFactory;
    public VolleyHelper volleyHelper;
    public TokenStorage tokenStorage;

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
    public VolleyHelper getVolleyHelper() {
        return volleyHelper;
    }

    @Override
    public TokenStorage getTokenStorage() {
        return tokenStorage;
    }
}
