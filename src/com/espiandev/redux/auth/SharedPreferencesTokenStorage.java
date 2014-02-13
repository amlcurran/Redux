package com.espiandev.redux.auth;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;

public class SharedPreferencesTokenStorage implements TokenStorage {
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private final SharedPreferences preferences;

    public SharedPreferencesTokenStorage(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public boolean storeToken(String token) {
        return token != null && preferences.edit().putString(KEY_AUTH_TOKEN, token).commit();
    }

    @Override
    public boolean hasToken() {
        return preferences.contains(KEY_AUTH_TOKEN);
    }

    @Override
    public String getToken() {
        return preferences.getString(KEY_AUTH_TOKEN, null);
    }

    @Override
    public String extractToken(String jsonResponseString) {
        try {
            JSONObject object = new JSONObject(jsonResponseString);
            return object.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}