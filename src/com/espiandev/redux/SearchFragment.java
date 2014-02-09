package com.espiandev.redux;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.espiandev.redux.auth.BasicFragment;
import com.espiandev.redux.network.Responder;

public class SearchFragment extends BasicFragment implements Responder<String> {

    private EditText queryField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        view.findViewById(R.id.search_submit).setOnClickListener(mSearchClickListener);
        queryField = (EditText) view.findViewById(R.id.search_query);
        return view;
    }

    @Override
    public int getTitle() {
        return R.string.search;
    }

    @Override
    public int getSubtitle() {
        return 0;
    }

    private View.OnClickListener mSearchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!TextUtils.isEmpty(queryField.getText())) {
                networkHelper.search(String.valueOf(queryField.getText()), SearchFragment.this);
            } else {
                titleHost.setSubtitle(getString(R.string.error_empty_search));
            }
        }
    };

    @Override
    public void onSuccessResponse(String response) {

    }

    @Override
    public void onErrorResponse(Exception error) {

    }
}
