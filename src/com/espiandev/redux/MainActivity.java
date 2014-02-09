package com.espiandev.redux;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.espiandev.redux.animation.AnimationFactory;
import com.espiandev.redux.animation.AnimationFactoryProvider;
import com.espiandev.redux.animation.RealAnimationFactory;
import com.espiandev.redux.auth.LoginFragment;
import com.espiandev.redux.auth.SharedPreferencesTokenStorage;
import com.espiandev.redux.auth.TokenStorage;
import com.espiandev.redux.auth.TokenStorageProvider;
import com.espiandev.redux.network.NetworkHelper;
import com.espiandev.redux.network.NetworkHelperProvider;
import com.espiandev.redux.network.VolleyNetworkHelper;

public class MainActivity extends Activity implements TitleHost, AnimationFactoryProvider, NetworkHelperProvider,
        TokenStorageProvider, FragmentManager.OnBackStackChangedListener, LoginListener {

    private TextView highBanner;
    private TextView lowBanner;
    AnimationFactory animationFactory;
    private TokenStorage tokenStorage;
    private NetworkHelper networkHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        animationFactory = new RealAnimationFactory();
        networkHelper = new VolleyNetworkHelper(((ReduxApp) getApplication()).getRequestQueue());
        tokenStorage = new SharedPreferencesTokenStorage(this);
        highBanner = (TextView) findViewById(R.id.high_banner);
        lowBanner = (TextView) findViewById(R.id.low_banner);
        getFragmentManager().addOnBackStackChangedListener(this);
        if (!hasAuthToken()) {
            getFragmentManager().beginTransaction().add(R.id.host_frame, new LoginFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
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
    public NetworkHelper getNetworkHelper() {
        return networkHelper;
    }

    @Override
    public TokenStorage getTokenStorage() {
        return tokenStorage;
    }

    @Override
    public void onBackStackChanged() {
        Fragment current = getFragmentManager().findFragmentById(R.id.host_frame);
        if (current instanceof TitledItem) {
            setTitle(getString(((TitledItem) current).getTitle()));
            setSubtitle(getString(((TitledItem) current).getSubtitle()));
        }
    }

    @Override
    public void onLogin() {
        getFragmentManager().beginTransaction().replace(R.id.host_frame, new SearchFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
