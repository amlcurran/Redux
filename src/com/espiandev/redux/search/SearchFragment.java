package com.espiandev.redux.search;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.espiandev.redux.BasicFragment;
import com.espiandev.redux.R;
import com.espiandev.redux.ResourceStringProvider;

public class SearchFragment extends BasicFragment {

    private EditText queryField;
    private View loadingSpinner;
    private View queryHost;
    private SearchListener searchListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        view.findViewById(R.id.search_submit).setOnClickListener(mSearchClickListener);
        queryField = (EditText) view.findViewById(R.id.search_query);
        queryField.setOnEditorActionListener(editorActionListener);
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
                requestSearch();
            } else {
                titleHost.setSubtitle(getString(R.string.error_empty_search));
            }
        }
    };

    private void requestSearch() {
        searchListener.onSearchResult(String.valueOf(queryField.getText()));
    }

    private TextView.OnEditorActionListener editorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                requestSearch();
                return true;
            }
            return false;
        }
    };

}
