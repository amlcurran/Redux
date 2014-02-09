package com.espiandev.redux;

import org.json.JSONObject;

import java.util.List;

public interface SearchListener {
    public void onSearchResult(List<Asset> results);
}
