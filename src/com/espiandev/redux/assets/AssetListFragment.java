package com.espiandev.redux.assets;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.espiandev.redux.BasicFragment;
import com.espiandev.redux.R;
import com.espiandev.redux.ResourceStringProvider;

import java.util.ArrayList;

public class AssetListFragment extends BasicFragment implements AdapterView.OnItemClickListener {

    private static final String QUERY = "query";
    private static final String ASSET_LIST = "assetList";
    public ArrayAdapter<Asset> adapter;
    private ListView listview;
    private AssetSelectionListener selectionListener;

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
        listview.setOnItemClickListener(this);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof AssetSelectionListener) {
            selectionListener = (AssetSelectionListener) activity;
        } else {
            throw new ClassCastException("Host activity must implement AssetSelectionListener");
        }
    }

    @Override
    public CharSequence getHostedSubtitle(ResourceStringProvider stringProvider) {
        return stringProvider.getString(R.string.results_title, getArguments().getString(QUERY));
    }

    @Override
    public CharSequence getHostedTitle(ResourceStringProvider stringProvider) {
        return stringProvider.getString(R.string.search_results);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        selectionListener.onAssetSelected(adapter.getItem(i));
    }
}
