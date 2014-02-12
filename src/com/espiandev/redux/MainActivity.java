package com.espiandev.redux;

import android.app.DownloadManager;
import android.app.Fragment;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.Volley;
import com.espiandev.redux.animation.RealAnimationFactory;
import com.espiandev.redux.assets.Asset;
import com.espiandev.redux.assets.AssetDetailsFragment;
import com.espiandev.redux.assets.AssetListFragment;
import com.espiandev.redux.assets.AssetListParser;
import com.espiandev.redux.auth.LoginFragment;
import com.espiandev.redux.auth.SharedPreferencesTokenStorage;
import com.espiandev.redux.navigation.FragmentManagerStacker;
import com.espiandev.redux.network.VolleyNetworkHelper;
import com.espiandev.redux.search.SearchFragment;

import javax.net.ssl.SSLSocketFactory;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Fragment fragment = getTokenStorage().hasToken() ? new SearchFragment() : new LoginFragment();
        stacker.addFragment(fragment);
    }

    @Override
    public void onLogin() {
        stacker.removeFragment();
        stacker.addFragment(new SearchFragment());
    }

    @Override
    public void onAuthFailed() {
        stacker.pushFragment(new LoginFragment());
    }

    @Override
    public void onSearchResult(String query) {
        stacker.pushFragment(AssetListFragment.newInstance(query));
    }

    @Override
    public void onAssetSelected(Asset asset) {
        stacker.pushFragment(AssetDetailsFragment.newInstance(asset));
    }

    protected void createWorld() {
        HurlStack hurlStack = new HurlStack(null, (SSLSocketFactory) SSLSocketFactory.getDefault());
        RequestQueue requestQueue = Volley.newRequestQueue(this, hurlStack);
        animationFactory = new RealAnimationFactory();
        tokenStorage = new SharedPreferencesTokenStorage(PreferenceManager.getDefaultSharedPreferences(this));
        networkHelper = new VolleyNetworkHelper(requestQueue, super.getTokenStorage());
        stacker = new FragmentManagerStacker(this);
        listParser = new AssetListParser();
        downloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
    }

}
