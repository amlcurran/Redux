package com.espiandev.redux.auth;

import android.content.SharedPreferences;

public class SharedPreferencesTokenStorage implements TokenStorage {
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private final SharedPreferences preferences;

    public SharedPreferencesTokenStorage(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    @Override
    public boolean storeToken(String token) {
        return preferences.edit().putString(KEY_AUTH_TOKEN, token)
                .commit();
    }

    @Override
    public boolean hasToken() {
        return preferences.contains(KEY_AUTH_TOKEN);
    }

    @Override
    public String getToken() {
        return preferences.getString(KEY_AUTH_TOKEN, null);
    }
}