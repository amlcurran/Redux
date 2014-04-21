package com.espiandev.redux;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.MediaRouteDialogFactory;
import android.support.v7.media.MediaRouter;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import com.espiandev.redux.cast.CastableDevice;
import com.espiandev.redux.cast.ui.CastActivityIndicator;
import com.espiandev.redux.downloads.Downloader;
import com.espiandev.redux.downloads.DownloaderProvider;
import com.espiandev.redux.navigation.Stacker;
import com.espiandev.redux.network.NetworkHelper;
import com.espiandev.redux.network.NetworkHelperProvider;
import com.espiandev.redux.preferences.PreferencesActivity;
import com.espiandev.redux.search.SearchListener;

import java.util.List;

public abstract class BaseActivity extends FragmentActivity implements TitleHost, AnimationFactoryProvider,
        NetworkHelperProvider, TokenStorageProvider, SearchListener, AuthListener,
        AssetSelectionListener, AssetListParserProvider, DownloaderProvider,
        CastManagerProvider, View.OnClickListener, CastActivityIndicator {
    protected Stacker stacker;
    public AnimationFactory animationFactory;
    public TokenStorage tokenStorage;
    public NetworkHelper networkHelper;
    public AssetListParser listParser;
    public CastManager castManager;
    public Downloader downloader;
    protected TextView lowBanner;
    protected TextView highBanner;
    private CharSequence title;
    private Button preferencesButton;
    private TextView castText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createWorld();

        highBanner = (TextView) findViewById(R.id.high_banner);
        lowBanner = (TextView) findViewById(R.id.low_banner);
        castText = (TextView) findViewById(R.id.button_bar_cast);
        castText.setOnClickListener(this);
        preferencesButton = (Button) findViewById(R.id.button_bar_preferences);
        preferencesButton.setOnClickListener(this);
    }

    @Override
    public AssetListParser getListParser() {
        return listParser;
    }

    protected abstract void createWorld();

    @Override
    public void setTitle(CharSequence title) {
        if (!title.equals(this.title)) {
            animationFactory.fadeAndChangeText(highBanner, title);
            this.title = title;
        }
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

    @Override
    public void onClick(View v) {
        if (preferencesButton.equals(v)) {
            startActivity(new Intent(this, PreferencesActivity.class));
        } else if (castText.equals(v)) {
            MediaRouteDialogFactory.getDefault().onCreateChooserDialogFragment()
                .show(getSupportFragmentManager(), "mediaRouteChooser");
        }
    }

    @Override
    public void onCastDeviceDisconnected(CastableDevice route) {
        castText.setText(R.string.cast);
    }

    @Override
    public void onCastDevicesFound(List<MediaRouter.RouteInfo> routes) {
        castText.setEnabled(routes.size() > 0);
    }

    @Override
    public void onCastDeviceConnecting(String route) {
        castText.setText(getString(R.string.cast_connecting, route));
    }

    @Override
    public void onCastDeviceConnected(CastableDevice route) {
        castText.setText(getString(R.string.cast_connected, "device"));
    }
}
