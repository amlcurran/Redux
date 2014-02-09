package com.espiandev.redux;

import android.content.Context;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

public class SharedPreferencesTokenStorage implements TokenStorage {
    private Context context;

    public SharedPreferencesTokenStorage(Context context) {
        this.context = context;
    }

    @Override
    public boolean storeToken(String response) {
        try {
            JSONObject object = new JSONObject(response);
            String token = object.getString("token");
            PreferenceManager.getDefaultSharedPreferences(context).edit()
                    .putString("auth_token", token)
                    .apply();
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}