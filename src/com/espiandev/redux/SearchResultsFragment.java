package com.espiandev.redux;

import android.os.Bundle;

import java.util.ArrayList;

public class SearchResultsFragment extends BasicFragment {

    private static final String QUERY = "query";
    private static final String ASSET_LIST = "assetList";

    public static SearchResultsFragment newInstance(String query, ArrayList<Asset> assetList) {
        SearchResultsFragment fragment = new SearchResultsFragment();
        Bundle b = new Bundle();
        b.putString(QUERY, query);
        b.putParcelableArrayList(ASSET_LIST, assetList);
        return fragment;
    }

    @Override
    public CharSequence getHostedSubtitle(ResourceStringProvider stringProvider) {
        return null;
    }

    @Override
    public CharSequence getHostedTitle(ResourceStringProvider stringProvider) {
        return stringProvider.getString(R.string.results_title, getArguments().getString(QUERY));
    }



}
