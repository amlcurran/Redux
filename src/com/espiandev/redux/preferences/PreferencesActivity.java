package com.espiandev.redux.preferences;

import android.os.Bundle;
import android.view.View;

import com.espiandev.redux.BaseActivity;
import com.espiandev.redux.R;
import com.espiandev.redux.animation.RealAnimationFactory;
import com.espiandev.redux.assets.Asset;

public class PreferencesActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PreferencesFragment fragment = new PreferencesFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.host_frame, fragment)
                .commit();
        setTitle(fragment.getHostedTitle(this));
    }

    @Override
    protected void createWorld() {
        animationFactory = new RealAnimationFactory();
        findViewById(R.id.button_bar_cast).setVisibility(View.GONE);
        findViewById(R.id.button_bar_preferences).setVisibility(View.GONE);
    }

    @Override
    public void onAssetSelected(Asset asset) {

    }

    @Override
    public void onLogin() {

    }

    @Override
    public void onAuthFailed() {

    }

    @Override
    public void onSearchResult(String query) {

    }
}
