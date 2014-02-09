package com.espiandev.redux;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.espiandev.redux.animation.AnimationFactory;
import com.espiandev.redux.animation.RealAnimationFactory;
import com.espiandev.redux.auth.LoginFragment;
import com.espiandev.redux.auth.SharedPreferencesTokenStorage;
import com.espiandev.redux.auth.TokenStorage;
import com.espiandev.redux.auth.TokenStorageProvider;

public class MainActivity extends Activity implements TitleHost, AnimationFactoryProvider, VolleyHelperProvider,
        TokenStorageProvider {

    private TextView highBanner;
    private TextView lowBanner;
    AnimationFactory animationFactory;
    private TokenStorage tokenStorage;
    private VolleyHelper volleyHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animationFactory = new RealAnimationFactory();
        volleyHelper = new ApplicationVolleyHelper(this);
        tokenStorage = new SharedPreferencesTokenStorage(this);
        highBanner = (TextView) findViewById(R.id.high_banner);
        lowBanner = (TextView) findViewById(R.id.low_banner);
        if (!hasAuthToken()) {
            getFragmentManager().beginTransaction().add(R.id.host_frame, new LoginFragment())
                    .commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.host_frame, new SearchFragment())
                    .commit();
        }
    }

    private boolean hasAuthToken() {
        return PreferenceManager.getDefaultSharedPreferences(this).contains("auth_token");
    }

    @Override
    public void setTitle(CharSequence title) {
        animationFactory.fadeAndChangeText(highBanner, title);
    }

    @Override
    public void setSubtitle(CharSequence subtitle) {
        animationFactory.fadeAndChangeText(lowBanner, subtitle);
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
