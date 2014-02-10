package com.espiandev.redux;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

import com.espiandev.redux.animation.AnimationFactory;
import com.espiandev.redux.animation.AnimationFactoryProvider;
import com.espiandev.redux.animation.RealAnimationFactory;
import com.espiandev.redux.assets.Asset;
import com.espiandev.redux.assets.AssetDetailsFragment;
import com.espiandev.redux.assets.AssetListFragment;
import com.espiandev.redux.assets.AssetListParser;
import com.espiandev.redux.assets.AssetListParserProvider;
import com.espiandev.redux.assets.AssetSelectionListener;
import com.espiandev.redux.auth.LoginFragment;
import com.espiandev.redux.auth.LoginListener;
import com.espiandev.redux.auth.SharedPreferencesTokenStorage;
import com.espiandev.redux.auth.TokenStorage;
import com.espiandev.redux.auth.TokenStorageProvider;
import com.espiandev.redux.network.NetworkHelper;
import com.espiandev.redux.network.NetworkHelperProvider;
import com.espiandev.redux.network.VolleyNetworkHelper;
import com.espiandev.redux.search.SearchFragment;
import com.espiandev.redux.search.SearchListener;

import java.util.ArrayList;

public class MainActivity extends Activity implements TitleHost, AnimationFactoryProvider, NetworkHelperProvider,
        TokenStorageProvider, SearchListener, LoginListener, AssetSelectionListener, AssetListParserProvider {

    protected Stacker stacker;
    protected AnimationFactory animationFactory;
    protected TokenStorage tokenStorage;
    protected NetworkHelper networkHelper;
    private TextView highBanner;
    private TextView lowBanner;
    private AssetListParser listParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createWorld();

        highBanner = (TextView) findViewById(R.id.high_banner);
        lowBanner = (TextView) findViewById(R.id.low_banner);

        Fragment fragment = tokenStorage.hasToken() ? new SearchFragment() : new LoginFragment();
        stacker.addFragment(fragment);
    }

    protected void createWorld() {
        animationFactory = new RealAnimationFactory();
        tokenStorage = new SharedPreferencesTokenStorage(PreferenceManager.getDefaultSharedPreferences(this));
        networkHelper = new VolleyNetworkHelper(((ReduxApp) getApplication()).getRequestQueue(), tokenStorage);
        stacker = new FragmentManagerStacker(this);
        listParser = new AssetListParser();
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
        stacker.addFragment(new SearchFragment());
    }

    @Override
    public void onSearchResult(String query, ArrayList<Asset> results) {
        stacker.pushFragment(AssetListFragment.newInstance(query));
    }

    @Override
    public void onAssetSelected(Asset asset) {
        stacker.pushFragment(AssetDetailsFragment.newInstance(asset));
    }

    @Override
    public AssetListParser getListParser() {
        return listParser;
    }
}
