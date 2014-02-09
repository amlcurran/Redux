package com.espiandev.redux;

import java.util.ArrayList;

public interface SearchListener {
    public void onSearchResult(String query, ArrayList<Asset> results);
}
