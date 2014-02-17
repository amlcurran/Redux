package com.espiandev.redux.assets;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AssetListParser {

    public ArrayList<Asset> parseResultList(String jsonString) {
        ArrayList<Asset> result = new ArrayList<Asset>();
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONArray assetArray = jsonObject.getJSONObject("results").getJSONArray("assets");
            for (int i = 0; i < assetArray.length(); i++) {
                Asset asset = Asset.fromJsonObject(assetArray.getJSONObject(i));
                if (asset != null) {
                    result.add(asset);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

}
