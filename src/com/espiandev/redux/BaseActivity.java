package com.espiandev.redux;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.espiandev.redux.animation.AnimationFactory;
import com.espiandev.redux.animation.AnimationFactoryProvider;
import com.espiandev.redux.assets.AssetListParser;
import com.espiandev.redux.assets.AssetListParserProvider;
import com.espiandev.redux.assets.AssetSelectionListener;
import com.espiandev.redux.auth.LoginListener;
import com.espiandev.redux.auth.TokenStorage;
import com.espiandev.redux.auth.TokenStorageProvider;
import com.espiandev.redux.navigation.Stacker;
import com.espiandev.redux.network.NetworkHelper;
import com.espiandev.redux.network.NetworkHelperProvider;
import com.espiandev.redux.search.SearchListener;

public abstract class BaseActivity extends Activity implements TitleHost, AnimationFactoryProvider, NetworkHelperProvider, TokenStorageProvider, SearchListener, LoginListener, AssetSelectionListener, AssetListParserProvider {
    protected Stacker stacker;
    public AnimationFactory animationFactory;
    public TokenStorage tokenStorage;
    public NetworkHelper networkHelper;
    protected TextView highBanner;
    protected TextView lowBanner;
    public AssetListParser listParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createWorld();

        highBanner = (TextView) findViewById(R.id.high_banner);
        lowBanner = (TextView) findViewById(R.id.low_banner);
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
}
