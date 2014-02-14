package com.espiandev.redux;

import com.espiandev.redux.animation.AnimationFactory;
import com.espiandev.redux.animation.AnimationFactoryProvider;
import com.espiandev.redux.assets.AssetListParser;
import com.espiandev.redux.assets.AssetListParserProvider;
import com.espiandev.redux.assets.AssetSelectionListener;
import com.espiandev.redux.auth.AuthListener;
import com.espiandev.redux.auth.TokenStorage;
import com.espiandev.redux.auth.TokenStorageProvider;
import com.espiandev.redux.cast.CastManager;
import com.espiandev.redux.cast.CastManagerProvider;
import com.espiandev.redux.downloads.Downloader;
import com.espiandev.redux.downloads.DownloaderProvider;
import com.espiandev.redux.navigation.Stacker;
import com.espiandev.redux.network.NetworkHelper;
import com.espiandev.redux.network.NetworkHelperProvider;
import com.espiandev.redux.search.SearchListener;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public abstract class BaseActivity extends Activity implements TitleHost, AnimationFactoryProvider,
        NetworkHelperProvider, TokenStorageProvider, SearchListener, AuthListener,
        AssetSelectionListener, AssetListParserProvider, DownloaderProvider,
        CastManagerProvider {
    protected Stacker stacker;
    public AnimationFactory animationFactory;
    public TokenStorage tokenStorage;
    public NetworkHelper networkHelper;
    public AssetListParser listParser;
    public CastManager castManager;
    public Downloader downloader;
    protected TextView lowBanner;
    protected TextView highBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createWorld();

        highBanner = (TextView) findViewById(R.id.high_banner);
        lowBanner = (TextView) findViewById(R.id.low_banner);
    }

    @Override
    public AssetListParser getListParser() {
        return listParser;
    }

    protected abstract void createWorld();

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
    public CastManager getCastManager() {
        return castManager;
    }

    @Override
    public Downloader getDownloader() {
        return downloader;
    }
}
