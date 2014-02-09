package com.espiandev.redux;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.TextView;

import com.espiandev.redux.animation.AnimationFactory;
import com.espiandev.redux.animation.AnimationFactoryProvider;
import com.espiandev.redux.animation.RealAnimationFactory;
import com.espiandev.redux.auth.LoginFragment;
import com.espiandev.redux.auth.LoginListener;
import com.espiandev.redux.auth.SharedPreferencesTokenStorage;
import com.espiandev.redux.auth.TokenStorage;
import com.espiandev.redux.auth.TokenStorageProvider;
import com.espiandev.redux.network.NetworkHelper;
import com.espiandev.redux.network.NetworkHelperProvider;
import com.espiandev.redux.network.VolleyNetworkHelper;

import org.json.JSONObject;

public class MainActivity extends Activity implements TitleHost, AnimationFactoryProvider, NetworkHelperProvider,
        TokenStorageProvider, SearchListener, LoginListener {

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
        if (tokenStorage.hasToken()) {
            getFragmentManager().beginTransaction().add(R.id.host_frame, new SearchFragment())
                    .commit();
        } else {
            getFragmentManager().beginTransaction().add(R.id.host_frame, new LoginFragment())
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
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
    public void onLogin() {
        getFragmentManager().beginTransaction().replace(R.id.host_frame, new SearchFragment())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    @Override
    public void onSearchResult(JSONObject results) {

    }
}
