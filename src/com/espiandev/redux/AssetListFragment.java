package com.espiandev.redux;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class AssetListFragment extends BasicFragment {

    private static final String QUERY = "query";
    private static final String ASSET_LIST = "assetList";
    public ArrayAdapter<Asset> adapter;
    private ListView listview;

    public static AssetListFragment newInstance(String query, ArrayList<Asset> assetList) {
        AssetListFragment fragment = new AssetListFragment();
        Bundle b = new Bundle();
        b.putString(QUERY, query);
        b.putParcelableArrayList(ASSET_LIST, assetList);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return listview = (ListView) inflater.inflate(R.layout.fragment_results, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayList<Asset> list = getArguments().getParcelableArrayList(ASSET_LIST);
        adapter = new AssetListAdapter(getActivity(), list);
        listview.setAdapter(adapter);
    }

    @Override
    public CharSequence getHostedSubtitle(ResourceStringProvider stringProvider) {
        return stringProvider.getString(R.string.results_title, getArguments().getString(QUERY));
    }

    @Override
    public CharSequence getHostedTitle(ResourceStringProvider stringProvider) {
        return stringProvider.getString(R.string.search_results);
    }



}
