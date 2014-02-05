package com.espiandev.redux;

import android.net.Uri;

public class ReduxUrlHelper {

    private static final String REDUX_AUTHORITY = "i.bbcredux.com";
    private static final String PATH_LOGIN = "user/login";

    public String buildLoginUrl(String username, String password) {
        return new Uri.Builder().scheme("http")
                .authority(REDUX_AUTHORITY)
                .path(PATH_LOGIN)
                .appendQueryParameter("username", username)
                .appendQueryParameter("password", password)
                .build().toString();
    }
}
