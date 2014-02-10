package com.espiandev.redux.search;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.espiandev.redux.BasicFragment;
import com.espiandev.redux.R;
import com.espiandev.redux.ResourceStringProvider;
import com.espiandev.redux.assets.Asset;
import com.espiandev.redux.assets.AssetListParser;
import com.espiandev.redux.network.NetworkErrorTranslator;
import com.espiandev.redux.network.Responder;

import java.util.ArrayList;

public class SearchFragment extends BasicFragment implements Responder<String> {

    private EditText queryField;
    private View loadingSpinner;
    private View queryHost;
    private SearchListener searchListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        view.findViewById(R.id.search_submit).setOnClickListener(mSearchClickListener);
        queryField = (EditText) view.findViewById(R.id.search_query);
        loadingSpinner = view.findViewById(R.id.spinner);
        queryHost = view.findViewById(R.id.search_query_host);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof SearchListener) {
            searchListener = (SearchListener) activity;
        } else {
            throw new ClassCastException("Search fragment host must implement SearchListener");
        }
    }

    @Override
    public CharSequence getHostedTitle(ResourceStringProvider stringProvider) {
        return stringProvider.getString(R.string.search);
    }

    @Override
    public CharSequence getHostedSubtitle(ResourceStringProvider stringProvider) {
        return null;
    }

    private View.OnClickListener mSearchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!TextUtils.isEmpty(queryField.getText())) {
                launchSearchRequest();
            } else {
                titleHost.setSubtitle(getString(R.string.error_empty_search));
            }
        }
    };

    private void launchSearchRequest() {
        networkHelper.search(String.valueOf(queryField.getText()), this);
        animationFactory.upAndOut(queryHost);
        animationFactory.upAndIn(loadingSpinner);
    }

    @Override
    public void onSuccessResponse(String response) {
        ArrayList<Asset> assetList = new AssetListParser().parseResultList(response);
        searchListener.onSearchResult(String.valueOf(queryField.getText()), assetList);
    }

    @Override
    public void onErrorResponse(Exception error) {
        animationFactory.cancelAnimations(loadingSpinner, queryHost);
        animationFactory.downAndOut(loadingSpinner);
        animationFactory.downAndIn(queryHost);
        titleHost.setSubtitle(NetworkErrorTranslator.getErrorString(this, error));
    }
}
