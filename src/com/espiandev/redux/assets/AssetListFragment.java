package com.espiandev.redux.assets;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.espiandev.redux.BasicFragment;
import com.espiandev.redux.R;
import com.espiandev.redux.ResourceStringProvider;
import com.espiandev.redux.network.NetworkErrorTranslator;
import com.espiandev.redux.network.ReduxUrlHelper;
import com.espiandev.redux.network.Responder;

import java.util.ArrayList;

public class AssetListFragment extends BasicFragment implements AdapterView.OnItemClickListener, Responder<String>, AbsListView.OnScrollListener {

    private static final String QUERY = "query";
    public ArrayAdapter<Asset> adapter;
    private ListView listview;
    private AssetSelectionListener selectionListener;
    private AssetListParser assetListParser;
    private View loadingSpinner;
    private int pagesLoaded = 0;
    private boolean isLoading;
    private boolean morePagesAvailable = true;

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
        listview.setOnScrollListener(this);
        listview.setOnItemClickListener(this);
        networkHelper.search(getQuery(), this, 0);
        animationFactory.upAndOut(listview);
        animationFactory.upAndIn(loadingSpinner);
    }

    private String getQuery() {
        return getArguments().getString(QUERY);
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
        return stringProvider.getString(R.string.results_title, getQuery());
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
        isLoading = false;
        ArrayList<Asset> assetList = assetListParser.parseResultList(response);
        if (assetList.size() < ReduxUrlHelper.PAGE_SIZE) {
            morePagesAvailable = false;
        }
        adapter.addAll(assetList);
        pagesLoaded++;
        if (pagesLoaded == 1) {
            animationFactory.downAndIn(listview);
            animationFactory.downAndOut(loadingSpinner);
        }
    }

    @Override
    public void onErrorResponse(Exception error) {
        isLoading = false;
        titleHost.setSubtitle(NetworkErrorTranslator.getErrorString(this, error));
        animationFactory.downAndIn(listview);
        animationFactory.downAndOut(loadingSpinner);
        handleError(error);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // start loading more when there are five visible below the last screen
        if (firstVisibleItem > totalItemCount - visibleItemCount - 5 && !isLoading && morePagesAvailable) {
            networkHelper.search(getQuery(), this, pagesLoaded + 1);
            isLoading = true;
        }
    }
}
