package com.espiandev.redux.network;

import android.net.Uri;

import com.espiandev.redux.assets.Asset;

public class ReduxUrlHelper {

    private static final String REDUX_AUTHORITY = "i.bbcredux.com";
    private static final String PATH_LOGIN = "user/login";
    private static final String PATH_SEARCH = "asset/search";
    private static final String IMAGE_PATH = "asset/media/%1$s/%2$s/JPEG-1280x/image.jpg";
    private static final String DOWNLOAD_PATH = "asset/media/%1$s/%2$s/h264_mp4_hi_v1.1/%3$s.mp4";

    public String buildLoginUrl(String username, String password) {
        return String.valueOf(getBaseUrl()
                .path(PATH_LOGIN)
                .appendQueryParameter("username", username)
                .appendQueryParameter("password", password)
                .build());
    }

    public String buildSearchUrl(String query, String token) {
        return String.valueOf(getBaseUrl().path(PATH_SEARCH)
                .appendQueryParameter("q", query)
                .appendQueryParameter("token", token)
                .appendQueryParameter("limit", "20").build());
    }

    private Uri.Builder getBaseUrl() {
        return new Uri.Builder().scheme("https")
                .authority(REDUX_AUTHORITY);
    }

    public String buildImageUrl(String uuid, String key) {
        return String.valueOf(getBaseUrl().path(String.format(IMAGE_PATH, uuid, key))
                .build());
    }

    public String buildDownloadUrl(Asset asset) {
        return String.valueOf(getBaseUrl().path(String.format(DOWNLOAD_PATH, asset.getUuid(),
                asset.getKey(), asset.getName())));
    }
}
