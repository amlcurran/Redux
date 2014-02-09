package com.espiandev.redux.network;

import android.net.Uri;

public class ReduxUrlHelper {

    private static final String REDUX_AUTHORITY = "i.bbcredux.com";
    private static final String PATH_LOGIN = "user/login";
    private static final String PATH_SEARCH = "asset/search";

    public String buildLoginUrl(String username, String password) {
        return getBaseUrl()
                .path(PATH_LOGIN)
                .appendQueryParameter("username", username)
                .appendQueryParameter("password", password)
                .build().toString();
    }

    public String buildSearchUrl(String query, String token) {
        return String.valueOf(getBaseUrl().path(PATH_SEARCH)
                .appendQueryParameter("q", query)
                .appendQueryParameter("token", token).build());
    }

    private Uri.Builder getBaseUrl() {
        return new Uri.Builder().scheme("https")
                .authority(REDUX_AUTHORITY);
    }
}
