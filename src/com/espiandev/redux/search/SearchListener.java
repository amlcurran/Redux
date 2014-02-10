package com.espiandev.redux.search;

import com.espiandev.redux.assets.Asset;

import java.util.ArrayList;

public interface SearchListener {
    public void onSearchResult(String query, ArrayList<Asset> results);
}
