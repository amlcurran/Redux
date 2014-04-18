package com.espiandev.redux.testing;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.espiandev.redux.BaseActivity;
import com.espiandev.redux.R;
import com.espiandev.redux.assets.Asset;

public class FragmentTestingActivity extends BaseActivity {

    private Fragment fragment;
    private CharSequence title;
    private CharSequence subtitle;
    public boolean onLoginCalled;
    public boolean onSearchResultCalled;
    public boolean onAuthFailedCalled;
    public Asset selectedAsset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().add(R.id.host_frame, fragment)
                    .commit();
        }
    }

    @Override
    protected void createWorld() {

    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public CharSequence getTitleHostSubtitle() {
        return subtitle;
    }

    public CharSequence getTitleHostTitle() {
        return title;
    }

    @Override
    public void setTitle(CharSequence title) {
        this.title = title;
    }

    @Override
    public void setSubtitle(CharSequence subtitle) {
        this.subtitle = subtitle;
    }

    @Override
    public void onLogin() {
        onLoginCalled = true;
    }

    @Override
    public void onAuthFailed() {
        onAuthFailedCalled = true;
    }

    @Override
    public void onSearchResult(String query) {
        onSearchResultCalled = true;
    }

    @Override
    public void onAssetSelected(Asset asset) {
        selectedAsset = asset;
    }

}
