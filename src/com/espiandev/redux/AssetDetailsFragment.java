package com.espiandev.redux;

import android.os.Bundle;

public class AssetDetailsFragment extends BasicFragment {
    private static final String UUID = "uuid";
    private static final String TITLE = "title";
    private static final String ASSET = "asset";

    public static AssetDetailsFragment newInstance(Asset asset) {
        AssetDetailsFragment fragment = new AssetDetailsFragment();
        Bundle b = new Bundle();
        b.putParcelable(ASSET, asset);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public CharSequence getHostedSubtitle(ResourceStringProvider stringProvider) {
        return getAsset().getChannel().getDisplayName();
    }

    @Override
    public CharSequence getHostedTitle(ResourceStringProvider stringProvider) {
        return getAsset().getName();
    }

    private Asset getAsset() {
        return (Asset) getArguments().getParcelable(ASSET);
    }
}
