package com.espiandev.redux;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SearchResultsParser {

    public ArrayList<Asset> parseResultList(String jsonString) {
        ArrayList<Asset> result = new ArrayList<Asset>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray assetArray = jsonObject.getJSONObject("results").getJSONArray("assets");
            for (int i = 0; i < assetArray.length(); i++) {
                result.add(Asset.fromJsonObject(assetArray.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

}