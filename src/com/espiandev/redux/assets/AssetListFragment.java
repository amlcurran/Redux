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
import com.espiandev.redux.network.NetworkErrorTranslator;
import com.espiandev.redux.network.Responder;

import java.util.ArrayList;

public class AssetListFragment extends BasicFragment implements AdapterView.OnItemClickListener, Responder<String> {

    private static final String QUERY = "query";
    private static final String ASSET_LIST = "assetList";
    public ArrayAdapter<Asset> adapter;
    private ListView listview;
    private AssetSelectionListener selectionListener;
    private AssetListParser assetListParser;
    private ArrayList<Asset> assetList;
    private View loadingSpinner;

    public static AssetListFragment newInstance(String query) {
        AssetListFragment fragment = new AssetListFragment();
        Bundle b = new Bundle();
        b.putString(QUERY, query);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_results, container, false);
        listview = (ListView) view.findViewById(R.id.results_list);
        loadingSpinner = view.findViewById(R.id.spinner);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new AssetListAdapter(getActivity(), new ArrayList<Asset>());
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(this);
        networkHelper.search(getArguments().getString(QUERY), this, 0);
        animationFactory.upAndOut(listview);
        animationFactory.upAndIn(loadingSpinner);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof AssetListParserProvider) {
            assetListParser = ((AssetListParserProvider) activity).getListParser();
        }
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

    @Override
    public void onSuccessResponse(String response) {
        assetList = assetListParser.parseResultList(response);
        adapter.addAll(assetList);
        animationFactory.downAndIn(listview);
        animationFactory.downAndOut(loadingSpinner);
    }

    @Override
    public void onErrorResponse(Exception error) {
        titleHost.setSubtitle(NetworkErrorTranslator.getErrorString(this, error));
        animationFactory.downAndIn(listview);
        animationFactory.downAndOut(loadingSpinner);
        handleError(error);
    }

}
