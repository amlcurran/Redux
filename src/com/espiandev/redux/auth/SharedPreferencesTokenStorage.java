package com.espiandev.redux.auth;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONException;
import org.json.JSONObject;

public class SharedPreferencesTokenStorage implements TokenStorage {
    public static final String KEY_AUTH_TOKEN = "auth_token";
    private final SharedPreferences sharedPreferences;

    public SharedPreferencesTokenStorage(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    @Override
    public boolean storeToken(String response) {
        try {
            JSONObject object = new JSONObject(response);
            String token = object.getString("token");
            sharedPreferences.edit()
                    .putString(KEY_AUTH_TOKEN, token)
                    .apply();
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean hasToken() {
        return sharedPreferences.contains(KEY_AUTH_TOKEN);
    }

    @Override
    public String getToken() {
        return sharedPreferences.getString(KEY_AUTH_TOKEN, null);
    }
}